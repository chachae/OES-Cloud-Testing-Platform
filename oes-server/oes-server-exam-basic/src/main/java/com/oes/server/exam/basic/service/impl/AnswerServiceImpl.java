package com.oes.server.exam.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.entity.QueryParam;
import com.oes.common.core.exam.entity.Answer;
import com.oes.common.core.exam.entity.query.QueryAnswerDto;
import com.oes.common.core.util.SecurityUtil;
import com.oes.server.exam.basic.mapper.AnswerMapper;
import com.oes.server.exam.basic.service.IAnswerService;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chachae
 * @since 2020-06-03 16:43:14
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements IAnswerService {

  @Override
  public IPage<Answer> pageAnswer(QueryAnswerDto answer, QueryParam param) {
    return baseMapper.pageAnswer(answer, new Page<>(param.getPageNum(), param.getPageSize()));
  }

  @Override
  public List<Answer> getAnswer(Long studentId, Long paperId) {
    if (studentId == null) {
      studentId = SecurityUtil.getCurrentUser().getUserId();
    }
    LambdaQueryWrapper<Answer> wrapper = new LambdaQueryWrapper<>();
    wrapper
        .eq(Answer::getStudentId, studentId)
        .eq(Answer::getPaperId, paperId);
    return baseMapper.selectList(wrapper);
  }

  @Override
  public Answer getAnswer(Long studentId, Long paperId, Long questionId) {
    if (studentId == null) {
      studentId = SecurityUtil.getCurrentUser().getUserId();
    }
    LambdaQueryWrapper<Answer> wrapper = new LambdaQueryWrapper<>();
    wrapper
        .eq(Answer::getStudentId, studentId)
        .eq(Answer::getPaperId, paperId)
        .eq(Answer::getQuestionId, questionId);
    Answer answer = baseMapper.selectOne(wrapper);
    return answer != null ? answer : new Answer();
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteAnswer(Long userId, Long paperId) {
    if (userId != null || paperId != null) {
      LambdaQueryWrapper<Answer> wrapper = new LambdaQueryWrapper<>();
      if (userId != null) {
        wrapper.eq(Answer::getStudentId, userId);
      }
      if (paperId != null) {
        wrapper.eq(Answer::getPaperId, paperId);
      }
      baseMapper.delete(wrapper);
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateAnswer(Answer answer) {
    answer.setUpdateTime(new Date());
    answer.setStatus(Answer.STATUS_CORRECT);
    baseMapper.updateById(answer);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void createAnswer(Answer answer) {
    answer.setCreateTime(new Date());
    baseMapper.insert(answer);
  }
}