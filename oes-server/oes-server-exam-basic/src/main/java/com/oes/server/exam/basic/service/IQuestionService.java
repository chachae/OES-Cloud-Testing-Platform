package com.oes.server.exam.basic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.exam.entity.Question;
import com.oes.common.core.exam.entity.query.QueryQuestionDto;
import java.util.List;
import java.util.Map;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
public interface IQuestionService extends IService<Question> {

  /**
   * 分页搜索题目
   *
   * @param question 模糊搜索
   * @return {@link IPage<Question>} 分页结果集
   */
  IPage<Question> pageQuestion(QueryQuestionDto question);

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

  /**
   * 统计各科目题量排名前10的题目情况
   *
   * <pre>
   *   返回的集合内 Map 中的数据解释
   *   value：当前科目题目的总数
   *   name：当前课程名称
   * </pre>
   *
   * @return {@link List<Map>} 查询数据题
   */
  List<Map<String, Object>> getTopTenQuestionData();

  /**
   * 统计各类型题目的题目数量分布情况
   *
   * @return {@link List<Map>} 分布数据
   */
  List<Map<String, Object>> getTypeCountDistribute();

  /**
   * 通过课程编号查询题目数量
   *
   * @param courseIds 课程编号集合
   * @return 数量
   */
  Integer countByCourseIds(String[] courseIds);


  /**
   * 通过题目类型编号查询题目数量
   *
   * @param typeIds 题目类型编号
   * @return 数量
   */
  Integer countByTypeIds(String[] typeIds);
}