package com.oes.server.exam.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.entity.exam.CourseTeacher;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
public interface ICourseTeacherService extends IService<CourseTeacher> {

  /**
   * 通过课程编号删除课程-教师中间表数据
   *
   * @param courseId 课程编号
   */
  void deleteByCourseId(Long courseId);

  /**
   * 通过教师编号获取课程数据
   *
   * @param teacherId 教师编号
   * @return 课程编号集合
   */
  String getByTeacherId(Long teacherId);

}