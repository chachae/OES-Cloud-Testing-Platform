package com.oes.server.examination.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.entity.QueryParam;
import com.oes.common.core.exception.ApiException;
import com.oes.server.examination.entity.system.Paper;
import com.oes.server.examination.entity.system.PaperQuestion;
import com.oes.server.examination.entity.system.PaperType;
import com.oes.server.examination.entity.system.Question;
import com.oes.server.examination.mapper.PaperMapper;
import com.oes.server.examination.service.IPaperDeptService;
import com.oes.server.examination.service.IPaperQuestionService;
import com.oes.server.examination.service.IPaperService;
import com.oes.server.examination.service.IPaperTypeService;
import com.oes.server.examination.service.IQuestionService;
import com.oes.server.examination.service.IScoreService;
import com.oes.server.examination.util.PaperUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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
  private final IQuestionService questionService;
  private final IPaperTypeService paperTypeService;
  private final IPaperDeptService paperDeptService;
  private final IPaperQuestionService paperQuestionService;

  @Override
  public IPage<Paper> pagePaper(Paper paper, QueryParam param) {
    Page<Paper> page = new Page<>(param.getPageNum(), param.getPageSize());
    IPage<Paper> result = baseMapper.pagePaper(page, paper);
    // 对试卷试题类型分类排序
    if (result.getTotal() > 0L) {
      page.getRecords().forEach(PaperUtil::sortQuestions);
    }
    return result;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updatePaper(Paper paper) {
    paper.setUpdateTime(new Date());
    baseMapper.updateById(paper);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deletePaper(String[] paperIds) {
    if (canDeleted(paperIds)) {
      baseMapper.deleteBatchIds(Arrays.asList(paperIds));
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void randomCreatePaper(Paper paper, PaperType paperType) {
    paper.setCreateTime(new Date());
    paper.setIsRandom(Paper.IS_RANDOM);
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
      List<PaperQuestion> result = new ArrayList<>(questionNum);
      for (int c = 0; c < questionNum; c++) {
        result.add(new PaperQuestion(paper.getPaperId(), questions.get(c).getQuestionId()));
      }
      this.paperQuestionService.saveBatch(result);
    }
  }

  private void setPaperType(Long paperId, String[] typeIdArray, String[] scoreArray,
      String[] numArray) {
    List<PaperType> objects = new ArrayList<>(typeIdArray.length);
    for (int i = 0; i < typeIdArray.length; i++) {
      objects.add(
          new PaperType(paperId, Long.parseLong(typeIdArray[i]), Integer.parseInt(scoreArray[i]),
              Integer.parseInt(numArray[i])));
    }
    this.paperTypeService.saveBatch(objects);

  }

  private boolean canDeleted(String[] paperIds) {
    // 已指派班级 / 存在成绩数据：false，其余情况允许删除
    return CollUtil.isEmpty(paperDeptService.getByPaperId(paperIds)) || CollUtil
        .isEmpty(scoreService.getList(paperIds));
  }

}