package com.oes.server.exam.basic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.entity.QueryParam;
import com.oes.common.core.entity.exam.Answer;

/**
 * @author chachae
 * @since 2020-06-03 16:43:13
 */
public interface IAnswerService extends IService<Answer> {


  /**
   * 分页查询学生答案数据
   *
   * @param param  分页参数
   * @param answer 模糊条件
   * @return {@link IPage<Answer>} 分页结果集
   */
  IPage<Answer> pageAnswer(Answer answer, QueryParam param);

  /**
   * 删除学生答案数据
   * <p>
   * 1. 数据全部传入：删除该学生该门考试的答案 2. 只传 studentId：删除该名学生的全部答案数据，适用于删除学生信息 3. 只传
   * paperId：删除某门试卷的答案信息，适用于删除试卷信息
   * </p>
   *
   * @param userId  学生编号
   * @param paperId 试卷编号
   */
  void deleteAnswer(Long userId, Long paperId);

  /**
   * 删除学生答案数据
   *
   * @param answerIds 问题答案编号数组
   */
  void deleteAnswer(String[] answerIds);


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