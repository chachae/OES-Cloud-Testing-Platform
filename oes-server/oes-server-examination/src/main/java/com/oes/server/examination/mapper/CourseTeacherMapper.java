package com.oes.server.examination.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oes.server.examination.entity.system.CourseTeacher;
import java.util.List;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
public interface CourseTeacherMapper extends BaseMapper<CourseTeacher> {

  /**
   * 通过课程编号查询课程-教师中间信息
   *
   * @param courseId 课程编号
   * @return 课程-教师中间信息集合
   */
  List<CourseTeacher> selectList(Long courseId);

}