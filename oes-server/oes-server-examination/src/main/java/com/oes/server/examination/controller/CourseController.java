package com.oes.server.examination.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oes.common.core.entity.QueryParam;
import com.oes.common.core.entity.R;
import com.oes.common.core.util.PageUtil;
import com.oes.server.examination.entity.system.Course;
import com.oes.server.examination.service.ICourseService;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/4 14:58
 */
@Slf4j
@Validated
@RestController
@RequestMapping("course")
@RequiredArgsConstructor
public class CourseController {

  private final ICourseService courseService;

  @GetMapping
  @PreAuthorize("hasAuthority('course:view')")
  public R<Map<String, Object>> pageCourse(Course course, QueryParam param) {
    IPage<Course> result = courseService.pageCourse(course, param);
    return R.ok(PageUtil.toPage(result));
  }

  @GetMapping("check/{courseName}")
  public Boolean pageCourse(@PathVariable String courseName) {
    Course course = courseService
        .getOne(new LambdaQueryWrapper<Course>().eq(Course::getCourseName, courseName));
    return course == null;
  }

  @PutMapping
  @PreAuthorize("hasAuthority('course:update')")
  public void update(@Valid Course course) {
    this.courseService.updateCourse(course);
  }

  @PostMapping
  @PreAuthorize("hasAuthority('course:add')")
  public void add(@Valid Course course) {
    this.courseService.createCourse(course);
  }
}
