package com.oes.server.exam.basic.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.exam.entity.Course;
import com.oes.common.core.exam.entity.CourseTeacher;
import com.oes.common.core.exam.entity.Question;
import com.oes.common.core.exam.entity.query.QueryCourseDto;
import com.oes.common.core.exception.ApiException;
import com.oes.common.core.util.SecurityUtil;
import com.oes.server.exam.basic.mapper.CourseMapper;
import com.oes.server.exam.basic.mapper.QuestionMapper;
import com.oes.server.exam.basic.service.ICourseService;
import com.oes.server.exam.basic.service.ICourseTeacherService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

  private final QuestionMapper questionMapper;
  private final ICourseTeacherService courseTeacherService;

  @Override
  public IPage<Course> pageCourse(QueryCourseDto course) {
    return baseMapper.pageCourse(course, new Page<>(course.getPageNum(), course.getPageSize()));
  }

  @Override
  public List<Course> getList(QueryCourseDto course) {
    LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
    if (StrUtil.isNotBlank(course.getCourseName())) {
      wrapper.like(Course::getCourseName, course.getCourseName());
    }
    if (course.getDeptId() != null) {
      wrapper.eq(Course::getDeptId, course.getDeptId());
    }
    if (course.getCreatorId() != null) {
      wrapper.eq(Course::getCreatorId, course.getCreatorId());
    }
    return baseMapper.selectList(wrapper);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteCourse(String[] courseIds) {
    if (hasCourse(courseIds)) {
      throw new ApiException("当前题库包含相关课程的题目，请移除相关题目后重试");
    }
    baseMapper.deleteBatchIds(Arrays.asList(courseIds));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateCourse(Course course) {
    baseMapper.updateById(course);
    // 维护课程-教师数据
    courseTeacherService.deleteByCourseId(course.getCourseId());
    if (StrUtil.isNotBlank(course.getTeacherIds())) {
      setCourseTeacher(course.getCourseId(), StrUtil.split(course.getTeacherIds(), StrUtil.COMMA));
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void createCourse(Course course) {
    course.setCreatorId(SecurityUtil.getCurrentUserId());
    baseMapper.insert(course);
    // 维护课程-教师数据
    if (StrUtil.isNotBlank(course.getTeacherIds())) {
      setCourseTeacher(course.getCourseId(), StrUtil.split(course.getTeacherIds(), StrUtil.COMMA));
    }
  }

  private void setCourseTeacher(Long courseId, String[] teacherIds) {
    List<CourseTeacher> courseTeachers = new ArrayList<>(teacherIds.length);
    for (String teacherId : teacherIds) {
      courseTeachers.add(new CourseTeacher(courseId, Long.parseLong(teacherId)));
    }
    courseTeacherService.insertBatch(courseTeachers);
  }

  private boolean hasCourse(String[] courseIds) {
    return questionMapper.selectCount(new LambdaQueryWrapper<Question>().in(Question::getCourseId, Arrays.asList(courseIds))) > 0;
  }
}