package com.oes.oss.qiniu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.oss.qiniu.entity.QiNiuConfig;

/**
 * 七牛云配置表服务接口
 *
 * @author chachae
 * @since 2020-06-27 13:21:49
 */
public interface IQiNiuConfigService extends IService<QiNiuConfig> {


  /**
   * 查配置
   *
   * @return QiNiuConfig
   */
  QiNiuConfig getConfig();

  /**
   * 修改配置
   *
   * @param qiNiuConfig 配置
   */
  void updateConfig(QiNiuConfig qiNiuConfig);

}