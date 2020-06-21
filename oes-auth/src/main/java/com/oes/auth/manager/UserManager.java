package com.oes.auth.manager;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.oes.auth.mapper.CourseTeacherMapper;
import com.oes.auth.mapper.MenuMapper;
import com.oes.auth.mapper.PaperMapper;
import com.oes.auth.mapper.UserMapper;
import com.oes.auth.mapper.UserRoleMapper;
import com.oes.common.core.constant.SystemConstant;
import com.oes.common.core.entity.exam.CourseTeacher;
import com.oes.common.core.entity.exam.Paper;
import com.oes.common.core.entity.system.Menu;
import com.oes.common.core.entity.system.SystemUser;
import com.oes.common.core.entity.system.UserDataPermission;
import com.oes.common.core.entity.system.UserRole;
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
    List<CourseTeacher> cts = courseTeacherMapper.selectList(
        new LambdaQueryWrapper<CourseTeacher>().eq(CourseTeacher::getTeacherId, userId));

    return cts.stream().map(ct -> String.valueOf(ct.getCourseId()))
        .collect(Collectors.joining(StrUtil.COMMA));
  }

  /**
   * 获取试卷信息
   *
   * @param userId 用户编号
   * @return 试卷编号数据
   */
  public String finaPaper(Long userId) {
    List<Paper> papers = paperMapper
        .selectList(new LambdaQueryWrapper<Paper>().eq(Paper::getCreatorId, userId));

    return papers.stream().map(paper -> String.valueOf(paper.getPaperId()))
        .collect(Collectors.joining(StrUtil.COMMA));
  }

  /**
   * 注册用户
   *
   * @param username username
   * @param password password
   * @return SystemUser SystemUser
   */
  @Transactional(rollbackFor = Exception.class)
  public SystemUser registUser(String username, String password) {
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

}
