package com.oes.server.exam.basic.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oes.common.core.entity.R;
import com.oes.common.core.exam.entity.Course;
import com.oes.common.core.exam.entity.query.QueryCourseDto;
import com.oes.common.core.util.PageUtil;
import com.oes.server.exam.basic.service.ICourseService;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Course controller.
 *
 * @author chachae
 * @version v1.0
 * @date 2020/6/4 14:58
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("course")
public class CourseController {

  private final ICourseService courseService;

  @GetMapping
  @PreAuthorize("hasAuthority('course:view')")
  public R<Map<String, Object>> pageCourse(QueryCourseDto course) {
    IPage<Course> result = courseService.pageCourse(course);
    return R.ok(PageUtil.toPage(result));
  }

  /**
   * check course name s it repeated.
   *
   * @param courseName course name
   * @return {@link Boolean}
   */
  @GetMapping("check/{courseName}")
  public Boolean pageCourse(@PathVariable String courseName) {
    Course course = courseService
        .getOne(new LambdaQueryWrapper<Course>().eq(Course::getCourseName, courseName));
    return course == null;
  }

  @GetMapping("options")
  public R<List<Course>> options(QueryCourseDto course) {
    List<Course> result = this.courseService.getList(course);
    return R.ok(result);
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

  @DeleteMapping("{courseIds}")
  @PreAuthorize("hasAuthority('course:delete')")
  public void delete(@NotBlank(message = "{required}") @PathVariable String courseIds) {
    String[] courseIdArray = courseIds.split(StrUtil.COMMA);
    this.courseService.deleteCourse(courseIdArray);
  }
}
