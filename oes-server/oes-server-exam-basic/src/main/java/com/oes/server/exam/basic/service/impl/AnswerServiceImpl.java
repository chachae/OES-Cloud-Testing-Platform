package com.oes.server.exam.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.exam.entity.Answer;
import com.oes.common.core.exam.entity.query.QueryAnswerDto;
import com.oes.common.core.exam.util.GroupUtil;
import com.oes.common.core.util.SecurityUtil;
import com.oes.server.exam.basic.mapper.AnswerMapper;
import com.oes.server.exam.basic.service.IAnswerService;
import java.util.ArrayList;
import java.util.HashMap;
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
  public List<Answer> getAnswerList(Long userId, Long paperId) {
    // 处理用户名
    userId = userId == null ? SecurityUtil.getCurrentUserId() : userId;
    LambdaQueryWrapper<Answer> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(Answer::getUserId, userId).eq(Answer::getPaperId, paperId);
    return baseMapper.selectList(wrapper);
  }

  @Override
  public Map<Long, Answer> getAnswerMap(Long userId, Long paperId) {
    Map<Long, Answer> map = new HashMap<>();
    List<Answer> answerList = getAnswerList(userId, paperId);
    if (!answerList.isEmpty()) {
      // 答题记录组装到 HashMap 中
      answerList.forEach(answer -> map.put(answer.getQuestionId(), answer));
      return map;
    }
    return map;
  }

  @Override
  public List<Map<String, Object>> getWarnAnswerList(QueryAnswerDto entity) {
    entity.setUserId(SecurityUtil.getCurrentUserId());
    List<Answer> answers = baseMapper.selectWarnAnswerList(entity);
    return !answers.isEmpty() ? GroupUtil.groupQuestion(answers) : new ArrayList<>(0);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateAnswer(Answer answer) {
    answer.setStatus(Answer.STATUS_CORRECT);
    baseMapper.update(answer, new LambdaQueryWrapper<Answer>()
        .eq(Answer::getPaperId, answer.getPaperId())
        .eq(Answer::getQuestionId, answer.getQuestionId())
        .eq(Answer::getUserId, answer.getUserId()));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void createDefaultAnswer(long paperId, List<Long> userIds, List<Long> questionIds) {
    List<Answer> answers = new ArrayList<>();
    for (Long userId : userIds) {
      for (Long questionId : questionIds) {
        Answer answer = new Answer().createDefaultObject(paperId);
        answer.setUserId(userId);
        answer.setQuestionId(questionId);
        answers.add(answer);
      }
    }
    // 批量插入
    baseMapper.insertBatchSomeColumn(answers);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteByPaperId(Long paperId) {
    baseMapper.delete(new LambdaQueryWrapper<Answer>().eq(Answer::getPaperId, paperId));
  }
}