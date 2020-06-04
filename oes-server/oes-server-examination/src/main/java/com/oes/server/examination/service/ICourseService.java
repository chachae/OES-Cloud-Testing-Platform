package com.oes.server.examination.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.entity.QueryParam;
import com.oes.server.examination.entity.system.Course;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
public interface ICourseService extends IService<Course> {

  /**
   * 分页查询课程信息
   *
   * @param course 课程信息
   * @param param  分页数据
   * @return 分页结果集
   */
  IPage<Course> pageCourse(Course course, QueryParam param);


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