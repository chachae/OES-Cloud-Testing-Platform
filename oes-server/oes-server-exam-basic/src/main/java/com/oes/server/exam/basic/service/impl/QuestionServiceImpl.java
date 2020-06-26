package com.oes.server.exam.basic.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.constant.SystemConstant;
import com.oes.common.core.exam.entity.PaperQuestion;
import com.oes.common.core.exam.entity.Question;
import com.oes.common.core.exam.entity.Type;
import com.oes.common.core.exam.entity.query.QueryQuestionDto;
import com.oes.common.core.exception.ApiException;
import com.oes.common.core.util.SortUtil;
import com.oes.server.exam.basic.mapper.QuestionMapper;
import com.oes.server.exam.basic.service.IPaperQuestionService;
import com.oes.server.exam.basic.service.IQuestionService;
import java.util.Arrays;
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
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements
    IQuestionService {

  private final IPaperQuestionService paperQuestionService;

  @Override
  public IPage<Question> pageQuestion(QueryQuestionDto question) {
    Page<Question> page = new Page<>(question.getPageNum(), question.getPageSize());
    SortUtil.handlePageSort(question, page, "courseId", SystemConstant.ORDER_ASC, true);
    return baseMapper.pageQuestion(page, question);
  }

  @Override
  public List<Question> getList(Question question) {
    LambdaQueryWrapper<Question> wrapper = new LambdaQueryWrapper<>();
    if (question.getTypeId() != null) {
      wrapper.eq(Question::getTypeId, question.getTypeId());
    }
    if (question.getDifficult() != null) {
      wrapper.eq(Question::getDifficult, question.getDifficult());
    }
    if (question.getCourseId() != null) {
      wrapper.eq(Question::getCourseId, question.getCourseId());
    }
    if (StrUtil.isNotBlank(question.getQuestionName())) {
      wrapper.like(Question::getQuestionName, question.getQuestionName());
    }
    return baseMapper.selectList(wrapper);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void createQuestion(Question question) {
    sortMulChoiceRightKey(question);
    question.setCreateTime(new Date());
    baseMapper.insert(question);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteQuestion(String[] questionIds) {
    if (hasQuestion(questionIds)) {
      throw new ApiException("试题与试卷存在关联，请删除相关试卷后重试");
    }
    baseMapper.deleteBatchIds(Arrays.asList(questionIds));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateQuestion(Question question) {
    sortMulChoiceRightKey(question);
    question.setUpdateTime(new Date());
    baseMapper.updateById(question);
  }

  private void sortMulChoiceRightKey(Question question) {
    if (String.valueOf(question.getTypeId()).equals(Type.DEFAULT_TYPE_ID_ARRAY[1])) {
      String[] rightKeyArray = question.getRightKey().split(StrUtil.COMMA);
      Arrays.sort(rightKeyArray);
      question.setRightKey(String.join(StrUtil.COMMA, rightKeyArray));
    }
  }

  private boolean hasQuestion(String[] questionIds) {
    return this.paperQuestionService
        .count(new LambdaQueryWrapper<PaperQuestion>().in(PaperQuestion::getQuestionId,
            Arrays.asList(questionIds))) > 0;
  }
}