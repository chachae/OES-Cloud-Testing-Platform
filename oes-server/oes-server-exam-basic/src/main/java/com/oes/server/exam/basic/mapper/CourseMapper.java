package com.oes.server.exam.basic.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oes.common.core.exam.entity.Course;
import com.oes.common.core.exam.entity.query.QueryCourseDto;
import com.oes.common.exam.datasource.starter.annotation.ExamInfoScope;
import com.oes.server.exam.basic.enhance.EnhanceMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
@ExamInfoScope(methods = {"pageCourse"}, field = "course_id")
public interface CourseMapper extends EnhanceMapper<Course> {

  /**
   * 分页查询课程信息
   *
   * @param course 课程信息
   * @param param  分页数据
   * @return {@link IPage<Course>} 分页结果集
   */
  IPage<Course> pageCourse(@Param("course") QueryCourseDto course, Page<Course> param);
}