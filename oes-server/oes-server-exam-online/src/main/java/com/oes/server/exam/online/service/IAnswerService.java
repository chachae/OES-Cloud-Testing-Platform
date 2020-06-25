package com.oes.server.exam.online.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.entity.exam.Answer;
import java.util.List;

/**
 * @author chachae
 * @since 2020-06-03 16:43:13
 */
public interface IAnswerService extends IService<Answer> {

  /**
   * 获取学生答题集合
   *
   * @param studentId 学生编号
   * @param paperId   试卷编号
   * @return {@link List<Answer>} 分页结果集
   */
  List<Answer> getAnswer(Long studentId, Long paperId);

  /**
   * 更新答案
   *
   * @param answer 答案
   */
  Long updateAnswer(Answer answer);

}