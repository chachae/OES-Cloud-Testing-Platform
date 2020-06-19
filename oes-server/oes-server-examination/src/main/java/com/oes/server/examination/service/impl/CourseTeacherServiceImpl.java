package com.oes.server.examination.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.entity.examination.CourseTeacher;
import com.oes.server.examination.mapper.CourseTeacherMapper;
import com.oes.server.examination.service.ICourseTeacherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CourseTeacherServiceImpl extends
    ServiceImpl<CourseTeacherMapper, CourseTeacher> implements
    ICourseTeacherService {

  @Override
  public void deleteByCourseId(Long courseId) {
    baseMapper
        .delete(new LambdaQueryWrapper<CourseTeacher>().eq(CourseTeacher::getCourseId, courseId));
  }
}