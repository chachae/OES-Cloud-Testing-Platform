package com.oes.server.exam.basic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.entity.QueryParam;
import com.oes.common.core.entity.exam.Question;
import java.util.List;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
public interface IQuestionService extends IService<Question> {

  /**
   * 分页搜索题目
   *
   * @param param    分页数据
   * @param question 模糊搜索
   * @return {@link IPage<Question>} 分页结果集
   */
  IPage<Question> pageQuestion(QueryParam param, Question question);

  /**
   * 获取题目集合
   *
   * @param question 问题数据
   * @return {@link IPage<Question>} 分页结果集
   */
  List<Question> getList(Question question);

  /**
   * 增加题目
   *
   * @param question 题目信息
   */
  void createQuestion(Question question);

  /**
   * 删除题目
   *
   * @param questionIds 题目编号集合
   */
  void deleteQuestion(String[] questionIds);

  /**
   * 更新题目
   *
   * @param question 题目信息
   */
  void updateQuestion(Question question);
}