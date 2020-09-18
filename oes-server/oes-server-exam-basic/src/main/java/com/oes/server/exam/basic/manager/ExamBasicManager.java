package com.oes.server.exam.basic.manager;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.oes.common.core.constant.SystemConstant;
import com.oes.common.core.exam.entity.Course;
import com.oes.common.core.exam.entity.CourseTeacher;
import com.oes.common.core.exam.entity.Paper;
import com.oes.common.core.util.SecurityUtil;
import com.oes.server.exam.basic.mapper.CourseMapper;
import com.oes.server.exam.basic.mapper.CourseTeacherMapper;
import com.oes.server.exam.basic.mapper.PaperMapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/8/6 11:07
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ExamBasicManager {

  private final PaperMapper paperMapper;
  private final CourseMapper courseMapper;
  private final CourseTeacherMapper courseTeacherMapper;

  /**
   * 获取用户课程编号信息
   *
   * @param userId 用户编号
   * @return 课程编号信息
   */
  public String findUserCourse(Long userId) {
    return String.join(StrUtil.COMMA, getCourseIds(userId));
  }

  /**
   * 获取试卷权限信息
   *
   * <pre>
   *   逻辑：用课程编号（课程教师中间表中的数据）作为试卷操作权限是因为将试卷及其相关联的数据处理域（成绩、学生答案）是
   *   与试卷关联的，而实际的线下考试中出卷老师虽然也是只有一个，但是评卷和成绩录入工作是交由相应班级任课教师来处理的，
   *   此处可理解为增加课程的时候，指派教师信息即分配任课教师，出卷教师在他们其中选择，最终试卷是否正式使用仍需管理员进
   *   行试卷状态激活，此处的状态人为可控，不影响数据权限隔离，成绩和答案等关联信息则是与课程相关的教师都可操作的数据域。
   * </pre>
   *
   * @param userId 用户编号
   * @return 试卷编号数据
   */
  public String findPaper(Long userId) {
    List<String> courseIds = getCourseIds(userId);
    if (CollUtil.isNotEmpty(courseIds)) {
      List<Paper> papers = paperMapper
          .selectList(new LambdaQueryWrapper<Paper>().in(Paper::getCourseId, courseIds));

      return papers.stream().map(paper -> String.valueOf(paper.getPaperId()))
          .collect(Collectors.joining(StrUtil.COMMA));
    }
    return null;
  }

  private List<String> getCourseIds(Long userId) {

    String roleId = SecurityUtil.getCurrentUser().getRoleId();
    List<String> roleIds = StrUtil.split(roleId, ',');
    // 包含学院管理员
    if (roleIds.contains(String.valueOf(SystemConstant.COLLEGE_ADMIN_ROLE_ID))) {
      Long deptId = SecurityUtil.getCurrentUser().getDeptId();
      List<Course> courses = courseMapper
          .selectList(new LambdaQueryWrapper<Course>().eq(Course::getDeptId, deptId));
      return courses.stream().map(ct -> String.valueOf(ct.getCourseId()))
          .collect(Collectors.toList());
    }

    // 其他角色的获取方式（系统管理员在全县注解方面可进行忽略）
    List<CourseTeacher> cts = courseTeacherMapper.selectList(
        new LambdaQueryWrapper<CourseTeacher>().eq(CourseTeacher::getTeacherId, userId));
    return cts.stream().map(ct -> String.valueOf(ct.getCourseId())).collect(Collectors.toList());
  }
}
