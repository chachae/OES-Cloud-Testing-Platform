package com.oes.server.exam.basic.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.constant.DataSourceConstant;
import com.oes.common.core.exam.entity.Answer;
import com.oes.common.core.exam.entity.Paper;
import com.oes.common.core.exam.entity.PaperDept;
import com.oes.common.core.exam.entity.PaperQuestion;
import com.oes.common.core.exam.entity.PaperType;
import com.oes.common.core.exam.entity.Question;
import com.oes.common.core.exam.entity.Score;
import com.oes.common.core.exam.entity.query.QueryPaperDto;
import com.oes.common.core.exam.util.GroupUtil;
import com.oes.common.core.exception.ApiException;
import com.oes.server.exam.basic.config.SnowflakeConfig;
import com.oes.server.exam.basic.mapper.PaperMapper;
import com.oes.server.exam.basic.mapper.ScoreMapper;
import com.oes.server.exam.basic.service.IAnswerService;
import com.oes.server.exam.basic.service.IPaperDeptService;
import com.oes.server.exam.basic.service.IPaperQuestionService;
import com.oes.server.exam.basic.service.IPaperService;
import com.oes.server.exam.basic.service.IPaperTypeService;
import com.oes.server.exam.basic.service.IQuestionService;
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

  private final ScoreMapper scoreMapper;
  private final IAnswerService answerService;
  private final SnowflakeConfig snowflakeConfig;
  private final IQuestionService questionService;
  private final IPaperTypeService paperTypeService;
  private final IPaperDeptService paperDeptService;
  private final IPaperQuestionService paperQuestionService;

  @Override
  @DS(DataSourceConstant.SLAVE)
  public IPage<Paper> pagePaper(QueryPaperDto paper) {
    Page<Paper> page = new Page<>(paper.getPageNum(), paper.getPageSize());
    IPage<Paper> result = baseMapper.pagePaper(page, paper);
    // 对试卷试题类型分类排序
    if (result.getTotal() > 0L) {
      result.getRecords().forEach(GroupUtil::groupQuestions);
    }
    return result;
  }

  @Override
  @DS(DataSourceConstant.SLAVE)
  public Paper getPaper(Long paperId, String username) {
    // 从缓存中取出试卷
    Paper paper = baseMapper.selectByPaperId(paperId);
    // 题目顺序随机
    Collections.shuffle(paper.getPaperQuestionList());
    // 获取学生答题记录并组装成 Map，优化先前单次从数据库获取单题目的方式，最大程度降低访问数据库的压力
    List<Answer> answers = answerService.getAnswer(username, paperId);
    Map<Long, Answer> answerMap = new HashMap<>(answers.size());
    if (CollUtil.isNotEmpty(answers)) {
      answers.forEach(answer -> answerMap.put(answer.getQuestionId(), answer));
    }

    // 判断 Map 存在元素情况，为空说明没有答题记录直接略过 set 操作
    if (!answerMap.isEmpty()) {
      for (PaperQuestion paperQuestion : paper.getPaperQuestionList()) {
        Answer answer = answerMap.get(paperQuestion.getQuestionId());
        if (answer != null) {
          paperQuestion.setAnswerId(answer.getAnswerId());
          paperQuestion.setAnswerContent(answer.getAnswerContent());
        }
      }
    }

    // 试卷题型分类
    GroupUtil.groupQuestions(paper);
    return paper;
  }

  @Override
  @DS(DataSourceConstant.SLAVE)
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
    if (canDeleted(paperIds)) {
      baseMapper.deleteBatchIds(Arrays.asList(paperIds));
      paperDeptService.deleteBatchByPaperIds(paperIds);
    } else {
      throw new ApiException("试卷存在考试成绩等关联信息，无法删除");
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void randomCreatePaper(Paper paper, PaperType paperType) {
    // 采用雪花算法
    paper.setPaperId(snowflakeConfig.getId());
    paper.setCreateTime(new Date());
    paper.setIsRandom(Paper.IS_RANDOM);
    paper.setStatus(Paper.STATUS_CLOSE);
    baseMapper.insert(paper);
    Long paperId = paper.getPaperId();

    String[] typeIdArray = paperType.getTypeIds().split(StrUtil.COMMA);
    String[] scoreArray = paperType.getScores().split(StrUtil.COMMA);
    String[] numArray = paperType.getNums().split(StrUtil.COMMA);

    this.setPaperType(paperId, typeIdArray, scoreArray, numArray);
    this.setRandomQuestion(paper, paperType.getDifficult(), typeIdArray, numArray);
  }

  private void setRandomQuestion(Paper paper, Integer difficult, String[] typeIdArray,
      String[] numArray) {
    for (int i = 0; i < typeIdArray.length; i++) {
      Question obj = new Question();
      // 排除整体难度限制
      if (difficult != 0) {
        obj.setDifficult(difficult);
      }
      obj.setCourseId(paper.getCourseId());
      obj.setTypeId(Long.parseLong(typeIdArray[i]));
      List<Question> questions = this.questionService.getList(obj);
      int questionNum = Integer.parseInt(numArray[i]);
      if (questions.size() < questionNum) {
        throw new ApiException("试题数量不足，请调试题分布后重试");
      }
      // 随机重排序（如果题库对应的题目数量和试卷相应题目数量一致则不需要随机排序）
      if (questions.size() != questionNum) {
        Collections.shuffle(questions);
      }
      List<PaperQuestion> batchObjs = new ArrayList<>(questionNum);
      for (int c = 0; c < questionNum; c++) {
        batchObjs.add(new PaperQuestion(paper.getPaperId(), questions.get(c).getQuestionId()));
      }
      this.paperQuestionService.saveBatch(batchObjs);
    }
  }

  private void setPaperType(Long paperId, String[] typeIdArray, String[] scoreArray,
      String[] numArray) {
    List<PaperType> objects = new ArrayList<>(typeIdArray.length);
    for (int i = 0; i < typeIdArray.length; i++) {
      objects.add(new PaperType(paperId, Long.parseLong(typeIdArray[i]), Integer.parseInt(scoreArray[i]), Integer.parseInt(numArray[i])));
    }
    this.paperTypeService.saveBatch(objects);
  }

  private void setPaperDept(Long paperId, String[] deptIdArray) {
    ArrayList<PaperDept> batchObjs = new ArrayList<>(deptIdArray.length);
    for (String deptId : deptIdArray) {
      batchObjs.add(new PaperDept(paperId, Long.parseLong(deptId)));
    }
    this.paperDeptService.saveBatch(batchObjs);
  }

  private boolean canDeleted(String[] paperIds) {
    List<String> paperIdList = Arrays.asList(paperIds);
    // 已指派班级 / 存在成绩数据：false，其余情况允许删除
    return paperDeptService
        .count(new LambdaQueryWrapper<PaperDept>().in(PaperDept::getPaperId, paperIdList)) == 0 ||
        scoreMapper.selectCount(new LambdaQueryWrapper<Score>().in(Score::getPaperId, paperIdList))
            == 0;
  }
}
