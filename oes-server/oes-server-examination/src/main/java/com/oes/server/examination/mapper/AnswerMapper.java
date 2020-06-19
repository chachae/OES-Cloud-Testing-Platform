package com.oes.server.examination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oes.common.core.entity.examination.Answer;
import com.oes.common.datasource.starter.announcation.DataPermission;
import org.apache.ibatis.annotations.Param;

/**
 * @author chachae
 * @since 2020-06-03 16:43:13
 */
@DataPermission(methods = {"pageAnswer"}, field = "tp.course_id", limitAdmin = false)
public interface AnswerMapper extends BaseMapper<Answer> {

  /**
   * 分页查询学生答案数据
   *
   * @param answer 学生答案查询条件
   * @param page   分页数据
   * @return 分页结果集
   */
  IPage<Answer> pageAnswer(@Param("answer") Answer answer, Page<Answer> page);

}