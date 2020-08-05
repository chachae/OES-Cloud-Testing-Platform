package com.oes.server.exam.basic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.exam.entity.Answer;
import com.oes.common.core.exam.entity.query.QueryAnswerDto;
import java.util.List;

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
   * 获取学生答题数据
   *
   * @param username 用户名
   * @param paperId  试卷编号
   * @return {@link Answer} 信息
   */
  List<Answer> getAnswer(String username, Long paperId);

  /**
   * 删除学生答案数据
   * <p>
   * 1. 数据全部传入：删除该学生该门考试的答案 2. 只传 username：删除该名学生的全部答案数据，适用于删除学生信息 3. 只传
   * paperId：删除某门试卷的答案信息，适用于删除试卷信息
   * </p>
   *
   * @param username 用户名
   * @param paperId  试卷编号
   */
  void deleteAnswer(String username, Long paperId);

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

}