package com.oes.server.exam.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oes.common.core.exam.entity.Question;
import com.oes.common.core.exam.entity.query.QueryQuestionDto;
import com.oes.common.datasource.starter.announcation.DataPermission;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
@DataPermission(methods = {"pageQuestion"}, field = "course_id")
public interface QuestionMapper extends BaseMapper<Question> {

  /**
   * 分页查询试题类型数据
   *
   * @param page     分页信息
   * @param question 模糊搜索条件
   * @return 分页结果集
   */
  IPage<Question> pageQuestion(Page<Question> page, @Param("question") QueryQuestionDto question);

  /**
   * 统计各科目题量排名前10的题目情况
   * <pre>
   *   返回的集合内 Map 中的数据解释
   *   value：当前科目题目的总数
   *   name：当前课程名称
   * </pre>
   *
   * @return {@link List<Map>} 查询数据题
   */
  List<Map<String, Object>> selectTopTenQuestionData();

  /**
   * 统计各型题目的题目数量分布情况
   *
   * @return {@link List<Map>} 分布数据
   */
  List<Map<String, Object>> countDistributeGroupByType();

}