package com.oes.server.system.controller;

import com.oes.common.core.entity.R;
import com.oes.common.core.entity.system.IdCardVerify;
import com.oes.server.system.service.IIdCardVerifyService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 部门控制层
 *
 * @author chachae
 * @version v1.0
 * @date 2020/5/27 22:08
 */
@Slf4j
@Validated
@RestController
@RequestMapping("id-card/verify")
@RequiredArgsConstructor
public class IdCardVerifyController {

  private final IIdCardVerifyService iIdCardVerifyService;

  @GetMapping("me")
  public R<IdCardVerify> getMyIdCardVerifyInfo() {
    return R.ok(iIdCardVerifyService.getByUserId(null));
  }

  @PostMapping
  public void createIdCardVerify(@Valid IdCardVerify idCardVerify) {
    iIdCardVerifyService.createIdCardVerify(idCardVerify);
  }

  @GetMapping("check")
  public boolean checkVerifyInfo() {
    return iIdCardVerifyService.getByUserId(null) != null;
  }

  @PostMapping("auth")
  public boolean checkVerifyInfo(@Valid IdCardVerify idCardVerify) {
    return iIdCardVerifyService.auth(idCardVerify.getName(), idCardVerify.getNum());
  }
}
