package com.oes.server.examination.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oes.server.examination.entity.system.Course;
import org.apache.ibatis.annotations.Param;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
public interface CourseMapper extends BaseMapper<Course> {

  /**
   * 分页查询课程信息
   *
   * @param course 课程信息
   * @param param  分页数据
   * @return 分页结果集
   */
  IPage<Course> pageCourse(@Param("course") Course course, Page<Course> param);

}