package com.oes.server.exam.basic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.entity.QueryParam;
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
   * @param param  分页参数
   * @param answer 模糊条件
   * @return {@link IPage<Answer>} 分页结果集
   */
  IPage<Answer> pageAnswer(QueryAnswerDto answer, QueryParam param);

  /**
   * 获取学生答题集合
   *
   * @param studentId 学生编号
   * @param paperId   试卷编号
   * @return {@link List<Answer>} 分页结果集
   */
  List<Answer> getAnswer(Long studentId, Long paperId);

  /**
   * 获取学生答题数据
   *
   * @param studentId  学生编号
   * @param paperId    试卷编号
   * @param questionId 问题编号
   * @return {@link Answer} 信息
   */
  Answer getAnswer(Long studentId, Long paperId, Long questionId);

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