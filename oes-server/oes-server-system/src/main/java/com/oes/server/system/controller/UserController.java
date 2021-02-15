package com.oes.server.system.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oes.common.core.entity.OptionTree;
import com.oes.common.core.entity.QueryParam;
import com.oes.common.core.entity.R;
import com.oes.common.core.entity.system.LoginLog;
import com.oes.common.core.entity.system.SystemUser;
import com.oes.common.core.util.PageUtil;
import com.oes.common.core.util.SecurityUtil;
import com.oes.server.system.annotation.ControllerEndpoint;
import com.oes.server.system.service.ILoginLogService;
import com.oes.server.system.service.IUserDataPermissionService;
import com.oes.server.system.service.IUserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户信息控制层
 *
 * @author chachae
 * @since 2020/4/30 19:56
 */
@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

  private final IUserService userService;
  private final PasswordEncoder passwordEncoder;
  private final IUserDataPermissionService userDeptService;
  private final ILoginLogService loginLogService;

  /**
   * 登录成功存储登录日志
   */
  @GetMapping("success")
  public void loginSuccess() {
    String currentUsername = SecurityUtil.getCurrentUsername();
    // 更新登录时间
    this.userService.updateLoginTime(currentUsername);
    LoginLog loginLog = new LoginLog();
    loginLog.setUsername(currentUsername);
    this.loginLogService.saveLoginLog(loginLog);
  }

  /**
   * Dashboard 数据统计
   *
   * @return R<Map < String, Object>>
   */
  @GetMapping("index")
  public R<Map<String, Object>> index() {
    Map<String, Object> data = new HashMap<>(7);
    // 获取系统总访问记录
    Long totalVisitCount = this.loginLogService.getTotalVisitCount();
    data.put("totalVisitCount", totalVisitCount);
    // 获取系统今日访问记录
    Long todayVisitCount = this.loginLogService.getTodayVisitCount();
    data.put("todayVisitCount", todayVisitCount);
    // 获取系统进入访问IP量
    Long todayIp = this.loginLogService.getTodayIp();
    data.put("todayIp", todayIp);
    // 获取近期系统访问记录
    List<Map<String, Object>> lastTenVisitCount = this.loginLogService.getLastTenDaysVisitCount(null);
    data.put("lastTenVisitCount", lastTenVisitCount);
    // 当前用户近七天访问记录
    SystemUser systemUser = new SystemUser();
    systemUser.setUsername(SecurityUtil.getCurrentUsername());
    List<Map<String, Object>> lastTenUserVisitCount = this.loginLogService
        .getLastTenDaysVisitCount(systemUser);
    data.put("lastTenUserVisitCount", lastTenUserVisitCount);
    return R.ok(data);
  }

  /**
   * 用户名检测
   *
   * @param username 用户名
   * @return boolean
   */
  @GetMapping("check")
  public boolean checkUserName(@NotBlank(message = "{required}") String username) {
    SystemUser result = this.userService.getSystemUser(username);
    return result == null;
  }

  @GetMapping("count")
  public R<Integer> getDeptCount(@NotBlank(message = "{required}") String deptIds) {
    Integer count = this.userService.count(new LambdaQueryWrapper<SystemUser>().in(SystemUser::getDeptId, StrUtil.split(deptIds, ',')));
    return R.ok(count);
  }

  /**
   * 用户名检测
   *
   * @param user 用户数据
   * @return boolean
   */
  @GetMapping("options")
  public R<List<OptionTree<SystemUser>>> getTreeOption(SystemUser user) {
    List<OptionTree<SystemUser>> tree = this.userService.getSystemUserTree(user);
    return R.ok(tree);
  }

  @GetMapping
  @PreAuthorize("hasAuthority('user:view')")
  public R<Map<String, Object>> userList(QueryParam param, SystemUser user) {
    IPage<SystemUser> result = this.userService.pageSystemUser(param, user);
    return R.ok(PageUtil.toPage(result));
  }

  @GetMapping("{userId}")
  public R<String> findUserDataPermissions(@NotNull(message = "{required}") @PathVariable Long userId) {
    String dataPermissions = this.userDeptService.getByUserId(userId);
    return R.ok(dataPermissions);
  }

  @GetMapping("user-id")
  public R<List<Long>> getUserIds(@NotNull(message = "{required}") String deptIds) {
    String[] ids = deptIds.split(StrUtil.COMMA);
    List<Long> userIds = this.userService.getUserIdByDeptIds(ids);
    return R.ok(userIds);
  }

  @PostMapping
  @PreAuthorize("hasAuthority('user:add')")
  @ControllerEndpoint(operation = "新增用户", exceptionMessage = "新增用户失败")
  public void addUser(@Valid SystemUser user) {
    this.userService.createUser(user);
  }

  @PutMapping
  @PreAuthorize("hasAuthority('user:update')")
  @ControllerEndpoint(operation = "更新用户", exceptionMessage = "更新用户失败")
  public void updateUser(@Valid SystemUser user) {
    this.userService.updateUser(user);
  }

  @DeleteMapping("{userIds}")
  @PreAuthorize("hasAuthority('user:delete')")
  @ControllerEndpoint(operation = "删除用户", exceptionMessage = "删除用户失败")
  public void deleteUsers(@NotBlank(message = "{required}") @PathVariable String userIds) {
    String[] ids = userIds.split(StrUtil.COMMA);
    this.userService.deleteUsers(ids);
  }

  @PutMapping("profile")
  @ControllerEndpoint(exceptionMessage = "修改个人信息失败")
  public void updateProfile(@Valid SystemUser user) {
    this.userService.updateProfile(user);
  }

  @PutMapping("avatar")
  @ControllerEndpoint(exceptionMessage = "修改头像失败")
  public void updateAvatar(@NotBlank(message = "{required}") String avatar) {
    this.userService.updateAvatar(avatar);
  }

  @GetMapping("password/check")
  public boolean checkPassword(@NotBlank(message = "{required}") String password) {
    String curUsername = SecurityUtil.getCurrentUsername();
    SystemUser user = this.userService.getSystemUser(curUsername);
    return user != null && this.passwordEncoder.matches(password, user.getPassword());
  }

  @PutMapping("password")
  @ControllerEndpoint(exceptionMessage = "修改密码失败")
  public void updatePassword(@NotBlank(message = "{required}") String password) {
    this.userService.updatePassword(password);
  }

  @PutMapping("password/reset")
  @PreAuthorize("hasAuthority('user:reset')")
  @ControllerEndpoint(exceptionMessage = "重置用户密码失败")
  public void resetPassword(@NotBlank(message = "{required}") String usernames) {
    String[] usernameArr = usernames.split(StrUtil.COMMA);
    this.userService.resetPassword(usernameArr);
  }

}
