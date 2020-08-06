package com.oes.server.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.constant.DataSourceConstant;
import com.oes.common.core.entity.auth.CurrentUser;
import com.oes.common.core.entity.system.IdCardVerify;
import com.oes.common.core.exception.ApiException;
import com.oes.common.core.util.DimUtil;
import com.oes.common.core.util.SecurityUtil;
import com.oes.server.system.mapper.IdCardVerifyMapper;
import com.oes.server.system.service.IIdCardVerifyService;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 身份核验表服务实现类
 *
 * @author chachae
 * @since 2020-06-30 22:33:15
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class IdCardVerifyServiceImpl extends
    ServiceImpl<IdCardVerifyMapper, IdCardVerify> implements
    IIdCardVerifyService {

  @Override
  @DS(DataSourceConstant.SLAVE)
  public IdCardVerify getByUserId(Long userId) {
    if (userId == null) {
      userId = SecurityUtil.getCurrentUserId();
    }

    IdCardVerify verifyInfo = baseMapper
        .selectOne(new LambdaQueryWrapper<IdCardVerify>().eq(IdCardVerify::getUserId, userId));

    // 身份证和姓名脱敏
    if (verifyInfo != null) {
      verifyInfo.setName(DimUtil.fullName(verifyInfo.getName()));
      verifyInfo.setNum(DimUtil.idCard(verifyInfo.getNum()));
    }

    return verifyInfo;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void createIdCardVerify(IdCardVerify idCardVerify) {
    CurrentUser curUser = SecurityUtil.getCurrentUser();
    // 判断是身份信息是否合法
    if (!StrUtil.equals(curUser.getFullName(), idCardVerify.getName())) {
      throw new ApiException("证件信息与当前用户信息比对失败，请重新上传");
    }
    idCardVerify.setUserId(curUser.getUserId());
    idCardVerify.setCreateTime(new Date());
    // 去除 base64 图片头信息
    idCardVerify.setPhoto(idCardVerify.getPhoto().substring(22));
    baseMapper.insert(idCardVerify);
  }

  @Override
  @DS(DataSourceConstant.SLAVE)
  public Boolean auth(String fullName, String idCardNum) {
    return baseMapper.selectCount(new LambdaQueryWrapper<IdCardVerify>()
        .eq(IdCardVerify::getUserId, SecurityUtil.getCurrentUserId())
        .eq(IdCardVerify::getNum, idCardNum)
        .eq(IdCardVerify::getName, fullName)) > 0;
  }
}