package com.oes.oss.qiniu.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.constant.DataSourceConstant;
import com.oes.oss.qiniu.entity.QiNiuConfig;
import com.oes.oss.qiniu.mapper.QiNiuConfigMapper;
import com.oes.oss.qiniu.service.IQiNiuConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * 七牛云配置表服务实现类
 *
 * @author chachae
 * @since 2020-06-27 13:21:50
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class QiNiuConfigServiceImpl extends ServiceImpl<QiNiuConfigMapper, QiNiuConfig> implements
    IQiNiuConfigService {

  @Override
  @DS(DataSourceConstant.SLAVE)
  public QiNiuConfig getConfig() {
    return baseMapper.selectById(1L);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateConfig(QiNiuConfig qiNiuConfig) {
    qiNiuConfig.setConfigId(1L);
    baseMapper.deleteById(1L);
    baseMapper.insert(qiNiuConfig);
  }
}