package com.oes.server.examination.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.entity.QueryParam;
import com.oes.server.examination.entity.system.Question;

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
   * @return 分页结果集
   */
  IPage<Question> pageQuestion(QueryParam param, Question question);

}