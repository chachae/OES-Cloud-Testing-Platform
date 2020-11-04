package com.oes.ai.function.face.badiu.service.impl;

import com.oes.ai.client.IdCardVerifyClient;
import com.oes.ai.entity.face.FaceMatchInfo;
import com.oes.ai.entity.face.QueryFaceMatch;
import com.oes.ai.function.face.badiu.client.IRemoteBaiduFaceMatchService;
import com.oes.ai.function.face.badiu.constant.BaiduFaceMatchConstant;
import com.oes.ai.function.face.badiu.service.IBaiduFaceMatchService;
import com.oes.ai.function.face.badiu.util.FaceMatchResultUtil;
import com.oes.ai.function.supplier.baidu.CommonErrorEnum;
import com.oes.ai.function.supplier.baidu.service.IAccessTokenService;
import com.oes.common.core.entity.R;
import com.oes.common.core.entity.system.IdCardVerify;
import com.oes.common.core.exception.ApiException;
import com.oes.common.core.util.JSONUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/7/9 21:36
 */
@Service
@RequiredArgsConstructor
public class BaiduFaceMatchServiceImpl implements IBaiduFaceMatchService {

  private final IdCardVerifyClient idCardVerifyClient;
  private final IAccessTokenService accessTokenService;
  private final IRemoteBaiduFaceMatchService remoteBaiduFaceMatchService;

  @Override
  public FaceMatchInfo faceMatch(QueryFaceMatch matchA) {

    List<Map<String, Object>> params = new ArrayList<>(2);

    Map<String, Object> targetA = new HashMap<>();
    Map<String, Object> targetB = new HashMap<>();

    // 检测目标A
    targetA.put(BaiduFaceMatchConstant.KEY_IMAGE, matchA.getImage());
    targetA.put(BaiduFaceMatchConstant.KET_IMAGE_TYPE, matchA.getImageType());
    targetA.put(BaiduFaceMatchConstant.KEY_FACE_TYPE, BaiduFaceMatchConstant.TYPE_FACE_LIVE);
    targetA.put(BaiduFaceMatchConstant.KEY_QUALITY_CONTROL, BaiduFaceMatchConstant.CONTROL_LOW);
    // 活体控制
    targetA.put(BaiduFaceMatchConstant.KEY_LIVENESS_CONTROL, BaiduFaceMatchConstant.CONTROL_HIGH);

    // 检测目标B
    targetB.put(BaiduFaceMatchConstant.KEY_IMAGE, getCurUserPhoto());
    targetB.put(BaiduFaceMatchConstant.KET_IMAGE_TYPE, BaiduFaceMatchConstant.TYPE_IMAGE_BASE64);
    targetB.put(BaiduFaceMatchConstant.KEY_FACE_TYPE, BaiduFaceMatchConstant.TYPE_FACE_IDCARD);
    targetB.put(BaiduFaceMatchConstant.KEY_QUALITY_CONTROL, BaiduFaceMatchConstant.CONTROL_LOW);
    // 身份证裁剪人像，身份核验阶段进行真实性检测，不需要活体控制
    targetB.put(BaiduFaceMatchConstant.KEY_LIVENESS_CONTROL, BaiduFaceMatchConstant.CONTROL_NONE);

    params.add(targetA);
    params.add(targetB);

    ResponseEntity<Map<String, Object>> responseEntity = remoteBaiduFaceMatchService
        .faceMatch(accessTokenService.getAccessToken(), JSONUtil.encode(params));
    Map<String, Object> body = responseEntity.getBody();

    if (body != null) {
      if (hasErrorResult(body)) {
        throw new ApiException(
            CommonErrorEnum.getErrorMsg((int) body.get(BaiduFaceMatchConstant.KEY_ERR_CODE)));
      } else {
        return FaceMatchResultUtil.mapToMatchInfo(body);
      }
    }

    return null;
  }

  /**
   * 获取当前用户的身份核验信息图片（身份证人像裁剪）
   *
   * @return {@link String} 图片 Base64 信息
   */
  private String getCurUserPhoto() {
    R<IdCardVerify> info = idCardVerifyClient.getMyIdCardVerifyInfo();
    if (info.getData() == null) {
      throw new ApiException("当前用户身份信息未核验");
    }
    // System.out.println(info.getData().getPhoto());
    return info.getData().getPhoto();
  }

  /**
   * 检查接口异常响应数据
   *
   * @param body 响应体数据
   * @return {@link Boolean} true / false
   */
  private boolean hasErrorResult(Map<String, Object> body) {
    final String okMsg = "SUCCESS";
    Object errKey = body.get(BaiduFaceMatchConstant.KEY_ERR_CODE);
    Object errMsg = body.get(BaiduFaceMatchConstant.KEY_ERR_MSG);
    return !errMsg.equals(okMsg) || (int) errKey != 0;
  }
}
