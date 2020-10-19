package com.oes.server.exam.basic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.exam.entity.Answer;
import com.oes.common.core.exam.entity.Paper;
import com.oes.common.core.exam.entity.query.QueryAnswerDto;
import java.util.List;
import java.util.Map;

/**
 * @author chachae
 * @since 2020-06-03 16:43:13
 */
public interface IAnswerService extends IService<Answer> {


  /**
   * 分页查询学生答案数据
   *
   * @param answer 模糊条件
   * @return {@link IPage<Answer>} 分页结果集
   */
  IPage<Answer> pageAnswer(QueryAnswerDto answer);

  /**
   * 获取学生答题数据
   *
   * @param username   用户名
   * @param paperId    试卷编号
   * @param questionId 问题编号
   * @return {@link Answer} 信息
   */
  Answer getAnswer(String username, Long paperId, Long questionId);

  /**
   * 获取考试答题数据
   *
   * @param paperId  试卷编号
   * @return {@link Answer} 信息
   */
  List<Answer> getAnswerList(Long paperId);

  /**
   * 获取学生答题数据
   *
   * @param username 用户名
   * @param paperId  试卷编号
   * @return {@link Answer} 信息
   */
  List<Answer> getAnswerList(String username, Long paperId);


  /**
   * 获取学生答题数据
   *
   * @param entity 查询信息
   * @return {@link List<Map<String, Object>>} 信息
   */
  List<Map<String, Object>> getWarnAnswerList(QueryAnswerDto entity);

  /**
   * 更新学生答案数据
   *
   * @param answer 学生答案信息
   */
  void updateAnswer(Answer answer);

  /**
   * 增加学生答案数据
   *
   * @param answer 学生答案信息
   */
  void createAnswer(Answer answer);

  /**
   * 创建试卷题目的答案
   */
  Paper createDefaultAnswer(Paper paper);

}