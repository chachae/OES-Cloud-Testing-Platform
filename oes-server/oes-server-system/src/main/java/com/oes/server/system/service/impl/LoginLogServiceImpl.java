package com.oes.server.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.constant.DataSourceConstant;
import com.oes.common.core.constant.SystemConstant;
import com.oes.common.core.entity.QueryParam;
import com.oes.common.core.entity.UserAgent;
import com.oes.common.core.entity.system.LoginLog;
import com.oes.common.core.entity.system.SystemUser;
import com.oes.common.core.util.HttpUtil;
import com.oes.common.core.util.SortUtil;
import com.oes.server.system.mapper.LoginLogMapper;
import com.oes.server.system.service.ILoginLogService;
import com.oes.server.system.util.AddressUtil;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 登录日志服务实现类
 *
 * @author chachae
 * @since 2020-05-27 17:11:48
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements
    ILoginLogService {

  @Override
  @DS(DataSourceConstant.SLAVE)
  public IPage<LoginLog> pageLoginLogs(LoginLog loginLog, QueryParam param) {
    LambdaQueryWrapper<LoginLog> qw = new LambdaQueryWrapper<>();

    if (StrUtil.isNotBlank(loginLog.getUsername())) {
      qw.eq(LoginLog::getUsername, loginLog.getUsername().toLowerCase());
    }
    if (StrUtil.isNotBlank(loginLog.getLoginTimeFrom()) && StrUtil
        .isNotBlank(loginLog.getLoginTimeTo())) {
      qw.ge(LoginLog::getLoginTime, loginLog.getLoginTimeFrom())
          .le(LoginLog::getLoginTime, loginLog.getLoginTimeTo());
    }

    Page<LoginLog> page = new Page<>(param.getPageNum(), param.getPageSize());
    SortUtil.handlePageSort(param, page, "loginTime", SystemConstant.ORDER_DESC, true);

    return this.page(page, qw);
  }

  @Override
  public void saveLoginLog(LoginLog loginLog) {
    String ip = HttpUtil.getIpAddress();
    loginLog.setIp(ip);
    loginLog.setLoginTime(new Date());
    loginLog.setLocation(AddressUtil.getCityInfo(ip));
    UserAgent ua = HttpUtil.getUserAgent();
    loginLog.setBrowser(ua.getBrowser());
    loginLog.setSystem(ua.getSystem());
    this.save(loginLog);
  }

  @Override
  @DS(DataSourceConstant.SLAVE)
  public Long getTotalVisitCount() {
    return baseMapper.countTotalVisit();
  }

  @Override
  @DS(DataSourceConstant.SLAVE)
  public Long getTodayVisitCount() {
    return baseMapper.countTodayVisit();
  }

  @Override
  @DS(DataSourceConstant.SLAVE)
  public Long getTodayIp() {
    return baseMapper.countTodayIp();
  }

  @Override
  @DS(DataSourceConstant.SLAVE)
  public List<Map<String, Object>> getLastTenDaysVisitCount(SystemUser user) {
    return baseMapper.selectLastTenDaysVisitCount(user);
  }

  @Override
  @DS(DataSourceConstant.SLAVE)
  public List<LoginLog> getUserLastSevenLoginLogs(String username) {
    LoginLog loginLog = new LoginLog();
    loginLog.setUsername(username);
    // 近7日记录
    QueryParam queryParam = new QueryParam(1L, 7L);
    IPage<LoginLog> loginLogs = pageLoginLogs(loginLog, queryParam);
    return loginLogs.getRecords();
  }

  @Override
  public void deleteLoginLogs(String[] ids) {
    List<String> list = Arrays.asList(ids);
    baseMapper.deleteBatchIds(list);
  }

}