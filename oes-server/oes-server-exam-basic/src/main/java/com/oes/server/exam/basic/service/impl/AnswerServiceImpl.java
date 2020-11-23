package com.oes.server.exam.basic.service.impl;

import cn.hutool.core.util.StrUtil;
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
  public List<Answer> getAnswerList(String username, Long paperId) {
    // 处理用户名
    username = StrUtil.isBlank(username) ? SecurityUtil.getCurrentUsername() : username;
    LambdaQueryWrapper<Answer> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(Answer::getUsername, username).eq(Answer::getPaperId, paperId);
    return baseMapper.selectList(wrapper);
  }

  @Override
  public Map<Long, Answer> getAnswerMap(String username, Long paperId) {
    Map<Long, Answer> map = new HashMap<>();
    List<Answer> answerList = getAnswerList(username, paperId);
    if (!answerList.isEmpty()) {
      // 答题记录组装到 HashMap 中
      answerList.forEach(answer -> map.put(answer.getQuestionId(), answer));
      return map;
    }
    return map;
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
  @Transactional(rollbackFor = Exception.class)
  public void createDefaultAnswer(Paper paper) {
    List<PaperQuestion> paperQuestionList = paper.getPaperQuestionList();
    String username = SecurityUtil.getCurrentUsername();
    for (PaperQuestion paperQuestion : paperQuestionList) {
      // 回答问题的预置信息
      Answer answer = new Answer().createDefaultObject(username, paper.getPaperId(), paperQuestion.getQuestionId());
      baseMapper.insert(answer);
      paperQuestion.setAnswerId(answer.getAnswerId());
    }
    List<Map<String, Object>> maps = GroupUtil.groupQuestionListByTypeId(paperQuestionList);
    paper.setPaperQuestions(maps);
    paper.setPaperQuestionList(null);
  }
}