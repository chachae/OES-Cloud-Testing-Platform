package com.oes.server.exam.basic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.exam.entity.Course;
import com.oes.common.core.exam.entity.query.QueryCourseDto;
import java.util.List;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
public interface ICourseService extends IService<Course> {

  /**
   * 分页查询课程信息
   *
   * @param course 课程信息
   * @return {@link IPage<Course>} 分页结果集
   */
  IPage<Course> pageCourse(QueryCourseDto course);

  /**
   * 获取课程集合（Mapper 携带数据权限）
   *
   * @param course 课程信息
   * @return {@link List<Course>} 课程集合
   */
  List<Course> getList(QueryCourseDto course);

  /**
   * 删除课程信息
   *
   * @param typeIds 课程编号集合
   */
  void deleteCourse(String[] typeIds);

  /**
   * 更新课程信息
   *
   * @param course 课程信息
   */
  void updateCourse(Course course);

  /**
   * 增加课程信息
   *
   * @param course 课程信息
   */
  void createCourse(Course course);
}