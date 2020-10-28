package com.oes.server.exam.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.exam.entity.Answer;
import com.oes.common.core.exam.entity.Paper;
import com.oes.common.core.exam.entity.PaperQuestion;
import com.oes.common.core.exam.entity.query.QueryAnswerDto;
import com.oes.common.core.exam.util.GroupUtil;
import com.oes.common.core.util.SecurityUtil;
import com.oes.server.exam.basic.mapper.AnswerMapper;
import com.oes.server.exam.basic.service.IAnswerService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chachae
 * @since 2020-06-03 16:43:14
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements IAnswerService {

  @Override
  public IPage<Answer> pageAnswer(QueryAnswerDto answer) {
    return baseMapper.pageAnswer(answer, new Page<>(answer.getPageNum(), answer.getPageSize()));
  }

  @Override
  public Answer getAnswer(String username, Long paperId, Long questionId) {
    LambdaQueryWrapper<Answer> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(Answer::getUsername, username).eq(Answer::getPaperId, paperId).eq(Answer::getQuestionId, questionId);
    return baseMapper.selectOne(wrapper);
  }

  @Override
  public List<Answer> getAnswerList(Long paperId) {
    return baseMapper.selectList(new LambdaQueryWrapper<Answer>().eq(Answer::getPaperId, paperId));
  }

  @Override
  public List<Answer> getAnswerList(String username, Long paperId) {
    LambdaQueryWrapper<Answer> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(Answer::getUsername, username).eq(Answer::getPaperId, paperId);
    return baseMapper.selectList(wrapper);
  }

  @Override
  public List<Map<String, Object>> getWarnAnswerList(QueryAnswerDto entity) {
    entity.setUsername(SecurityUtil.getCurrentUsername());
    List<Answer> answers = baseMapper.selectWarnAnswerList(entity);
    return !answers.isEmpty() ? GroupUtil.groupQuestion(answers) : new ArrayList<>(0);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateAnswer(Answer answer) {
    answer.setUsername(SecurityUtil.getCurrentUsername());
    answer.setStatus(Answer.STATUS_CORRECT);
    baseMapper.updateById(answer);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void createAnswer(Answer answer) {
    baseMapper.insert(answer);
  }

  @Override
  public Paper createDefaultAnswer(Paper paper) {
    List<PaperQuestion> paperQuestionList = paper.getPaperQuestionList();
    for (PaperQuestion paperQuestion : paperQuestionList) {
      Answer answer = new Answer();
      answer.setUsername(SecurityUtil.getCurrentUsername());
      answer.setWarn(Answer.IS_WARN);
      answer.setPaperId(paper.getPaperId());
      answer.setStatus(Answer.STATUS_NOT_CORRECT);
      answer.setScore(0);
      answer.setQuestionId(paperQuestion.getQuestionId());
      baseMapper.insert(answer);
      paperQuestion.setAnswerId(answer.getAnswerId());
    }
    GroupUtil.groupQuestions(paper, true);
    return paper;
  }
}