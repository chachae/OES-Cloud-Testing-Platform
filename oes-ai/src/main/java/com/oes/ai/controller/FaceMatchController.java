package com.oes.ai.controller;

import com.oes.ai.entity.face.FaceMatchInfo;
import com.oes.ai.entity.face.QueryFaceMatch;
import com.oes.ai.function.face.badiu.service.IBaiduFaceMatchService;
import com.oes.common.core.entity.R;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/7/9 20:51
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("face/match")
public class FaceMatchController {

  private final IBaiduFaceMatchService faceMatchService;

  @PostMapping
  public R<FaceMatchInfo> curUserFaceMatch(@Valid QueryFaceMatch matchInfo) {
    return R.ok(this.faceMatchService.faceMatch(matchInfo));
  }
}
