package com.oes.server.examination.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.entity.QueryParam;
import com.oes.server.examination.entity.system.Course;
import com.oes.server.examination.entity.system.CourseTeacher;
import com.oes.server.examination.mapper.CourseMapper;
import com.oes.server.examination.service.ICourseService;
import com.oes.server.examination.service.ICourseTeacherService;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

  private final ICourseTeacherService courseTeacherService;

  @Override
  public IPage<Course> pageCourse(Course course, QueryParam param) {
    Page<Course> page = new Page<>(param.getPageNum(), param.getPageSize());
    return baseMapper.pageCourse(course, page);
  }

  @Override
  public void deleteCourse(String[] typeIds) {
    // todo
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateCourse(Course course) {
    course.setUpdateTime(new Date());
    baseMapper.updateById(course);
    // 维护课程-教师数据
    courseTeacherService.deleteByCourseId(course.getCourseId());
    String[] teacherIds = StrUtil.split(course.getTeacherIds(), StrUtil.COMMA);
    setCourseTeacher(course.getCourseId(), teacherIds);
  }

  @Override
  public void createCourse(Course course) {
    course.setCreateTime(new Date());
    baseMapper.insert(course);
    // 维护课程-教师数据
    String[] teacherIds = StrUtil.split(course.getTeacherIds(), StrUtil.COMMA);
    setCourseTeacher(course.getCourseId(), teacherIds);
  }


  private void setCourseTeacher(Long courseId, String[] teacherIds) {
    for (String teacherId : teacherIds) {
      courseTeacherService.save(new CourseTeacher(courseId, Long.parseLong(teacherId)));
    }
  }
}