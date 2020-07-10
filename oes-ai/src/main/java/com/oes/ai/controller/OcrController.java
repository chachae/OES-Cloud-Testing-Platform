package com.oes.ai.controller;

import com.oes.ai.entity.ocr.IdCardInfo;
import com.oes.ai.entity.ocr.QueryOcrEntity;
import com.oes.ai.function.ocr.StrategyContext;
import com.oes.ai.function.ocr.aliyun.constant.AliyunOcrConstant;
import com.oes.ai.function.ocr.baidu.constant.BaiduOcrConstant;
import com.oes.common.core.entity.R;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/29 12:30
 */

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("ocr/id-card")
public class OcrController {

  private final StrategyContext strategyContext;

  @PostMapping("face")
  public R<IdCardInfo> checkIdCardFace(
      @RequestParam @NotNull(message = "{required}") MultipartFile file,
      @Valid QueryOcrEntity info) {
    info.setFile(file);
    info.setSide(BaiduOcrConstant.SIDE_FRONT);
    info.setType(AliyunOcrConstant.TYPE_OF_FACE);
    return R.ok(strategyContext.ocrIdCard(info));
  }

  @PostMapping("back")
  public R<IdCardInfo> checkIdCardBack(
      @RequestParam @NotNull(message = "{required}") MultipartFile file,
      @Valid QueryOcrEntity info) {
    info.setFile(file);
    info.setSide(BaiduOcrConstant.SIDE_BACK);
    info.setType(AliyunOcrConstant.TYPE_OF_BACK);
    return R.ok(strategyContext.ocrIdCard(info));
  }

}
