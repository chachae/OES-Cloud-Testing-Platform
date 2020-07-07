package com.oes.server.exam.online.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.exam.entity.Answer;
import com.oes.common.core.exam.entity.query.QueryAnswerDto;
import java.util.List;
import java.util.Map;

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
   * 获取学生错题集
   *
   * @param answer 查询条件
   * @return {@link List<Answer>} 错题信息
   */
  List<Map<String, Object>> getWarnAnswer(QueryAnswerDto answer);

  /**
   * 更新答案
   *
   * @param answer 答案
   */
  Long updateAnswer(Answer answer);

}