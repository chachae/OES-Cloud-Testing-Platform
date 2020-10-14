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
   * 获取学生答题集合
   *
   * @param username 用户名
   * @param paperId  试卷编号
   * @return {@link List<Answer>} 分页结果集
   */
  List<Answer> getAnswerList(String username, Long paperId);

  /**
   * 获取某张试卷的全部答题信息
   *
   * @param paperId 试卷编号
   * @return 题目信息
   */
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
   * 新增答案
   *
   * @param answer 答案
   */
  Long createAnswer(Answer answer);

  /**
   * 统计考试答题情况信息（只统计：单/多项选择题、填空题、判断题）
   *
   * @param paperId 试卷编号
   */
  List<Map<String, Object>> statisticAnswers(Long paperId);
}