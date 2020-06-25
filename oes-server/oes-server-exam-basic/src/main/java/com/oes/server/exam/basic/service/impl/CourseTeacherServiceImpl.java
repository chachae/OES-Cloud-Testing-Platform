package com.oes.server.exam.basic.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.exam.entity.CourseTeacher;
import com.oes.server.exam.basic.mapper.CourseTeacherMapper;
import com.oes.server.exam.basic.service.ICourseTeacherService;
import java.util.List;
import java.util.stream.Collectors;
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

  @Override
  public String getByTeacherId(Long teacherId) {
    List<CourseTeacher> cts = baseMapper.selectList(
        new LambdaQueryWrapper<CourseTeacher>().eq(CourseTeacher::getTeacherId, teacherId));

    return cts.stream().map(ct -> String.valueOf(ct.getCourseId()))
        .collect(Collectors.joining(StrUtil.COMMA));
  }
}