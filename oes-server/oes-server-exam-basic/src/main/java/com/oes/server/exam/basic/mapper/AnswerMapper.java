package com.oes.server.exam.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oes.common.core.exam.entity.Answer;
import com.oes.common.core.exam.entity.query.QueryAnswerDto;
import com.oes.common.datasource.starter.announcation.DataPermission;
import org.apache.ibatis.annotations.Param;

/**
 * @author chachae
 * @since 2020-06-03 16:43:13
 */
@DataPermission(methods = {"pageAnswer"}, field = "paper_id")
public interface AnswerMapper extends BaseMapper<Answer> {

  /**
   * 分页查询学生答案数据
   *
   * @param answer 学生答案查询条件
   * @param page   分页数据
   * @return 分页结果集
   */
  IPage<Answer> pageAnswer(@Param("answer") QueryAnswerDto answer, Page<Answer> page);

}