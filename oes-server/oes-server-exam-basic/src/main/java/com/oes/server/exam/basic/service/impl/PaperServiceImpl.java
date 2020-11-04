package com.oes.server.exam.basic.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.exam.entity.Answer;
import com.oes.common.core.exam.entity.Paper;
import com.oes.common.core.exam.entity.PaperDept;
import com.oes.common.core.exam.entity.PaperQuestion;
import com.oes.common.core.exam.entity.PaperType;
import com.oes.common.core.exam.entity.Question;
import com.oes.common.core.exam.entity.query.QueryPaperDto;
import com.oes.common.core.exam.util.GroupUtil;
import com.oes.common.core.exception.ApiException;
import com.oes.common.core.util.SecurityUtil;
import com.oes.server.exam.basic.mapper.PaperMapper;
import com.oes.server.exam.basic.service.IAnswerService;
import com.oes.server.exam.basic.service.IPaperDeptService;
import com.oes.server.exam.basic.service.IPaperQuestionService;
import com.oes.server.exam.basic.service.IPaperService;
import com.oes.server.exam.basic.service.IPaperTypeService;
import com.oes.server.exam.basic.service.IQuestionService;
import com.oes.server.exam.basic.service.IScoreService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PaperServiceImpl extends ServiceImpl<PaperMapper, Paper> implements IPaperService {

  private final IScoreService scoreService;
  private final IAnswerService answerService;
  private final IQuestionService questionService;
  private final IPaperTypeService paperTypeService;
  private final IPaperDeptService paperDeptService;
  private final IPaperQuestionService paperQuestionService;

  @Override
  public IPage<Paper> pagePaper(QueryPaperDto entity) {
    IPage<Paper> result = baseMapper.pagePaper(new Page<>(entity.getPageNum(), entity.getPageSize()), entity);
    // 对试卷试题类型分类排序
    if (result.getTotal() > 0L) {
      // 通过循环获取每张试卷的题目信息，进行设置和分类
      for (Paper paper : result.getRecords()) {
        List<PaperQuestion> paperQuestions = paperQuestionService.getListByPaperId(paper.getPaperId());
        paper.setPaperQuestionList(paperQuestions);
        GroupUtil.groupQuestions(paper, true);
      }
    }
    return result;
  }

  @Override
  public Paper getPaper(Long paperId, String username) {
    // 判断考试是否属于该考生
    List<Long> deptIds = paperDeptService.getDeptIdListByPaperId(paperId);
    if (!deptIds.isEmpty() && deptIds.contains(SecurityUtil.getCurrentUser().getDeptId())) {
      Paper paper = baseMapper.selectByPaperId(paperId);
      // 判断试卷是否存在
      if (paper == null) {
        return null;
      }
      // todo 题目顺序随机（获取试卷配置）
      List<PaperQuestion> paperQuestions = paperQuestionService.getListByPaperId(paperId);
      Collections.shuffle(paperQuestions);
      // 获取学生答题记录并组装成 Map，优化先前单次从数据库获取单题目的方式，最大程度降低访问数据库的压力（O(1)）
      List<Answer> answers = answerService.getAnswerList(username, paperId);
      // 答题记录不为空
      if (!answers.isEmpty()) {
        Map<Long, Answer> answerMap = new HashMap<>();
        // 答题记录组装到 HashMap 中
        answers.forEach(answer -> answerMap.put(answer.getQuestionId(), answer));
        for (PaperQuestion paperQuestion : paperQuestions) {
          Answer answer = answerMap.get(paperQuestion.getQuestionId());
          paperQuestion.setAnswerId(answer.getAnswerId());
          paperQuestion.setAnswerContent(answer.getAnswerContent());
        }
        // 试卷题型分类
        paper.setPaperQuestionList(paperQuestions);
        GroupUtil.groupQuestions(paper, true);
      } else {
        paper.setPaperQuestionList(paperQuestions);
      }
      return paper;
    }
    return null;
  }

  @Override
  public List<Map<String, Object>> getTopTenPaperData() {
    return baseMapper.selectTopTenPaper();
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updatePaper(Paper paper) {
    paper.setUpdateTime(new Date());
    baseMapper.updateById(paper);
    if (StrUtil.isNotBlank(paper.getDeptIds())) {
      // 维护试卷-课程数据
      paperDeptService.deleteByPaperId(paper.getPaperId());
      setPaperDept(paper.getPaperId(), paper.getDeptIds().split(StrUtil.COMMA));
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deletePaper(String[] paperIds) {
    // 检查试卷的班级指派和成绩关联系信息
    if (!checkPaperDept(paperIds)) {
      throw new ApiException("试卷存在班级关联信息，请删除后再试");
    }
    if (!checkPaperScore(paperIds)) {
      throw new ApiException("试卷存在成绩关联信息，请删除后再试");
    }
    // 删除试卷
    baseMapper.deleteBatchIds(Arrays.asList(paperIds));
    // 删除试卷关联班级
    paperDeptService.deleteBatchByPaperIds(paperIds);
    // 删除试卷关联试题编号
    paperQuestionService.deleteBatchByPaperIds(paperIds);
    // 删除试卷试题类型与分值数据
    paperTypeService.deleteBatchByPaperIds(paperIds);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void randomCreatePaper(Paper paper, PaperType paperType) {
    paper.setIsRandom(Paper.IS_RANDOM);
    paper.setStatus(Paper.STATUS_CLOSE);
    baseMapper.insert(paper);
    Long paperId = paper.getPaperId();

    String[] typeIdArray = paperType.getTypeIds().split(StrUtil.COMMA);
    String[] scoreArray = paperType.getScores().split(StrUtil.COMMA);
    String[] numArray = paperType.getNums().split(StrUtil.COMMA);

    this.setPaperType(paperId, typeIdArray, scoreArray, numArray);
    this.setPaperQuestion(paper, paperType.getDifficult(), typeIdArray, numArray);
  }

  private void setPaperQuestion(Paper paper, Integer difficult, String[] typeIdArray, String[] numArray) {
    // 预设置基础题目检索数据
    Question obj = new Question();
    // 排除整体难度限制
    if (difficult != 0) {
      obj.setDifficult(difficult);
    }
    obj.setCourseId(paper.getCourseId());
    for (int i = 0; i < typeIdArray.length; i++) {
      obj.setTypeId(Long.parseLong(typeIdArray[i]));
      List<Question> questions = this.questionService.getList(obj);
      int questionNum = Integer.parseInt(numArray[i]);

      if (questions.size() < questionNum) {
        throw new ApiException("试题数量不足，请调试题分布后重试");
      } else if (questions.size() != questionNum) {
        // 随机重排序（如果题库对应的题目数量和试卷相应题目数量一致则不需要随机排序）
        Collections.shuffle(questions);
      }

      List<PaperQuestion> batchObjs = new ArrayList<>(questionNum);
      for (int c = 0; c < questionNum; c++) {
        batchObjs.add(new PaperQuestion(paper.getPaperId(), questions.get(c).getQuestionId()));
      }
      this.paperQuestionService.saveBatch(batchObjs);
    }
  }

  private void setPaperType(Long paperId, String[] typeIdArray, String[] scoreArray, String[] numArray) {
    List<PaperType> ptList = new ArrayList<>(typeIdArray.length);
    for (int i = 0; i < typeIdArray.length; i++) {
      ptList.add(new PaperType(paperId, Long.parseLong(typeIdArray[i]), Integer.parseInt(scoreArray[i]), Integer.parseInt(numArray[i])));
    }
    this.paperTypeService.saveBatch(ptList);
  }

  private void setPaperDept(Long paperId, String[] deptIdArray) {
    List<PaperDept> batchObjs = new ArrayList<>(deptIdArray.length);
    for (String deptId : deptIdArray) {
      batchObjs.add(new PaperDept(paperId, Long.parseLong(deptId)));
    }
    this.paperDeptService.saveBatch(batchObjs);
  }

  private boolean checkPaperDept(String[] paperIds) {
    // 已指派班级则不允许删除
    return paperDeptService.countByPaperId(paperIds) == 0;
  }

  private boolean checkPaperScore(String[] paperIds) {
    // 存在成绩也不允许删除
    return scoreService.countByPaperId(paperIds) == 0;
  }
}
