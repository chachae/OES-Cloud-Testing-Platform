package com.oes.oss.qiniu.controller;

import com.oes.common.core.entity.R;
import com.oes.oss.qiniu.entity.QiNiuConfig;
import com.oes.oss.qiniu.service.IQiNiuConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 七牛云配置
 * <p>
 * 远端调用示例：http://domian:port/oss-qiniu/config | http://OES-OSS-Qiniu/oss-qiniu/config
 * </p>
 *
 * @author chachae
 * @version v1.0
 * @date 2020/6/27 14:18
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("config")
public class QiNiuConfigController {

  private final IQiNiuConfigService qiNiuConfigService;

  @GetMapping
  public R<QiNiuConfig> getConfig() {
    return R.ok(qiNiuConfigService.getConfig());
  }

  @PutMapping
  public void updateConfig(@Validated QiNiuConfig qiniuConfig) {
    qiNiuConfigService.updateConfig(qiniuConfig);
  }


}
