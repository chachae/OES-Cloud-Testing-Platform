package com.oes.server.exam.online.service;

import com.oes.common.core.exam.entity.Answer;
import java.util.List;
import java.util.Map;

/**
 * @author chachae
 * @since 2020-06-03 16:43:13
 */
public interface IAnswerService {

  /**
   * 获取某张试卷的全部答题信息<br>
   * 该接口是使用open-feign调用exam-basic服务answer业务下的获取答题集合的上层封装，
   * 目前已经完全废弃，并且对应的服务调用业务内没有该方法，在未来的版本中会被移除，请勿使用！
   *
   * @param paperId 试卷编号
   * @return 题目信息
   */
  @Deprecated
  List<Answer> getAnswer(Long paperId);

  /**
   * 获取学生错题集
   *
   * @param paperId 试卷编号
   * @return {@link List<Answer>} 错题信息
   */
  List<Map<String, Object>> getWarnAnswerByPaperId(Long paperId);

  /**
   * 更新答案
   *
   * @param answer 答案
   */
  Long updateAnswer(Answer answer);

  /**
   * 统计考试答题情况信息（只统计：单/多项选择题、填空题、判断题）<br>
   * 该接口是用于统计个题目的答题情况，开发未完成，请勿使用！
   *
   * @param paperId 试卷编号
   */
  @Deprecated
  List<Map<String, Object>> statisticAnswers(Long paperId);
}