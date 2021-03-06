package com.oes.server.exam.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.exam.entity.CourseTeacher;
import java.util.List;

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
   * 批量插入
   *
   * @param paperDept 实体
   */
  void insertBatch(List<CourseTeacher> paperDept);
}