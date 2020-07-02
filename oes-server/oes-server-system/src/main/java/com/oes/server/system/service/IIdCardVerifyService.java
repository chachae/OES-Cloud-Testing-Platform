package com.oes.server.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.entity.system.IdCardVerify;

/**
 * 身份核验表服务接口
 *
 * @author chachae
 * @since 2020-06-30 22:33:14
 */
public interface IIdCardVerifyService extends IService<IdCardVerify> {

  /**
   * 通过用户ID获取用户核验信息
   *
   * @param userId 用户ID
   * @return 核验数据
   */
  IdCardVerify getByUserId(Long userId);

  /**
   * 创建身份核验信息
   *
   * @param idCardVerify 身份证信息
   */
  void createIdCardVerify(IdCardVerify idCardVerify);

  /**
   * 检查当前用户身份核验信息是否匹配
   *
   * @param fullName  全名
   * @param idCardNum 身份证号
   * @return {@link Boolean} true / false
   */
  Boolean check(String fullName, String idCardNum);
}