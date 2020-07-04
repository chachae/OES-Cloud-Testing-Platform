package com.oes.auth.manager;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.oes.auth.mapper.CourseMapper;
import com.oes.auth.mapper.CourseTeacherMapper;
import com.oes.auth.mapper.MenuMapper;
import com.oes.auth.mapper.PaperMapper;
import com.oes.auth.mapper.UserMapper;
import com.oes.auth.mapper.UserRoleMapper;
import com.oes.common.core.constant.SystemConstant;
import com.oes.common.core.entity.system.Menu;
import com.oes.common.core.entity.system.SystemUser;
import com.oes.common.core.entity.system.UserDataPermission;
import com.oes.common.core.entity.system.UserRole;
import com.oes.common.core.exam.entity.Course;
import com.oes.common.core.exam.entity.CourseTeacher;
import com.oes.common.core.exam.entity.Paper;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户业务逻辑
 *
 * @author chachae
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserManager {

  private final CourseMapper courseMapper;
  private final UserMapper userMapper;
  private final MenuMapper menuMapper;
  private final UserRoleMapper userRoleMapper;
  private final PaperMapper paperMapper;
  private final CourseTeacherMapper courseTeacherMapper;

  /**
   * 通过用户名查询用户信息
   *
   * @param username 用户名
   * @return 用户
   */
  public SystemUser findByName(String username) {
    SystemUser user = userMapper.selectByName(username);
    if (user != null) {
      // 权限数据
      List<UserDataPermission> permissions = userMapper.selectUserDataPermissions(user.getUserId());
      String deptIds = permissions.stream().map(p -> String.valueOf(p.getDeptId()))
          .collect(Collectors.joining(
              StrUtil.COMMA));
      user.setDeptIds(deptIds);
    }
    return user;
  }

  /**
   * 通过用户名查询用户权限串
   *
   * @param username 用户名
   * @return 权限
   */
  public String findUserPermissions(String username) {
    List<Menu> userPermissions = menuMapper.selectUserPermissions(username);
    return userPermissions.stream().map(Menu::getPerms).collect(Collectors.joining(","));
  }

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
  public String finaPaper(Long userId) {
    List<String> courseIds = getCourseIds(userId);
    if (CollUtil.isNotEmpty(courseIds)) {
      List<Paper> papers = paperMapper
          .selectList(new LambdaQueryWrapper<Paper>().in(Paper::getCourseId, courseIds));

      return papers.stream().map(paper -> String.valueOf(paper.getPaperId()))
          .collect(Collectors.joining(StrUtil.COMMA));
    }
    return null;
  }

  /**
   * 注册用户
   *
   * @param username username
   * @param password password
   * @return SystemUser SystemUser
   */
  @Transactional(rollbackFor = Exception.class)
  public SystemUser registerUser(String username, String password) {
    SystemUser systemUser = new SystemUser();
    systemUser.setUsername(username);
    systemUser.setPassword(password);
    systemUser.setCreateTime(new Date());
    systemUser.setStatus(SystemUser.STATUS_VALID);
    systemUser.setSex(SystemUser.SEX_UNKNOWN);
    systemUser.setAvatar(SystemUser.DEFAULT_AVATAR);
    systemUser.setDescription("注册用户");
    this.userMapper.insert(systemUser);

    UserRole userRole = new UserRole();
    userRole.setUserId(systemUser.getUserId());
    // 注册用户角色 ID
    userRole.setRoleId(SystemConstant.REGISTER_ROLE_ID);
    this.userRoleMapper.insert(userRole);
    return systemUser;
  }

  private List<String> getCourseIds(Long userId) {

    // 获取用户角色信息
    List<Long> roles = Lists.transform(userRoleMapper
            .selectList(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userId)),
        UserRole::getRoleId);

    // 包含学院管理员
    if (roles.contains(SystemConstant.COLLEGE_ADMIN_ROLE_ID)) {
      Long deptId = userMapper.selectById(userId).getDeptId();
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
