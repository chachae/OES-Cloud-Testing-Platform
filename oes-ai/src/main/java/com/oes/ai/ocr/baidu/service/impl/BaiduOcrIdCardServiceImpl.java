package com.oes.ai.ocr.baidu.service.impl;

import cn.hutool.core.codec.Base64;
import com.oes.ai.ocr.baidu.IdCardNumTypeEnum;
import com.oes.ai.ocr.baidu.IdCardStatusEnum;
import com.oes.ai.ocr.baidu.constant.BaiduOcrConstant;
import com.oes.ai.ocr.baidu.remote.IRemoteBaiduOcrService;
import com.oes.ai.ocr.baidu.service.IAccessTokenService;
import com.oes.ai.ocr.baidu.service.IBaiduOcrIdCardService;
import com.oes.ai.ocr.baidu.util.ResultUtil;
import com.oes.ai.ocr.common.entity.BaseOcrEntity;
import com.oes.ai.ocr.common.entity.IdCardInfo;
import com.oes.common.core.exception.ApiException;
import com.oes.common.core.util.FileUtil;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/29 16:41
 */
@Slf4j
@Service("baiduOcrIdCardServiceImpl")
@RequiredArgsConstructor
public class BaiduOcrIdCardServiceImpl implements IBaiduOcrIdCardService {

  private final IAccessTokenService accessTokenService;
  private final IRemoteBaiduOcrService remoteBaiduOcrService;

  public IdCardInfo ocrIdCard(BaseOcrEntity entity) {
    // 获取接口资源访问权限 AccessToken
    String accessToken = accessTokenService.getAccessToken();

    // 获取 MultipartFile 对象，转 File 之后进行 Base64 编码
    String encode = Base64.encode(FileUtil.toFile(entity.getFile()));

    // 完成编码后的 Base64 字符串
    String imgParam;
    try {
      imgParam = URLEncoder.encode(encode, StandardCharsets.UTF_8.name());
    } catch (UnsupportedEncodingException e) {
      imgParam = "";
    }

    String side = entity.getSide();

    // 请求参数格式化（默认开启图片旋转裁剪检测、风险监测、完成性、头像裁剪）
    String params = String
        .format("detect_risk=%s&detect_photo=%s&detect_rectify=%s&id_card_side=%s&image=%s", true,
            true, true, side, imgParam);

    // Feign 远端调用
    ResponseEntity<Map<String, Object>> res = remoteBaiduOcrService.idCardOcr(accessToken, params);

    // 判断响应结果并转换响应体数据
    Map<String, Object> body = res.getBody();

    if (body != null) {
      // 判断识别状态是否正常（双面均需要识别）
      String status = body.get(BaiduOcrConstant.KEY_IMAGE_STATUS) + "";
      if (!IdCardStatusEnum.isNormal(status)) {
        throw new ApiException(IdCardStatusEnum.getValue(status));
      }

      // 判断信息一致性（脸部面）
      if (side.equals(BaiduOcrConstant.SIDE_FRONT)) {
        Integer typeCode = (int) body.get(BaiduOcrConstant.KEY_IDCARD_NUM_TYPE);
        if (!IdCardNumTypeEnum.isAllRight(typeCode)) {
          throw new ApiException(IdCardNumTypeEnum.getValue(typeCode));
        }
      }

      // 判断是否包含异常
      if (hasErrorResult(body)) {
        throw new ApiException(String.valueOf(body.get(BaiduOcrConstant.KEY_ERR_MSG)));
      } else {
        return ResultUtil.mapToIdCardInfo(side, body);
      }
    }
    return null;
  }

  /**
   * 检查接口异常响应数据
   *
   * @param body 响应体数据
   * @return {@link Boolean} true / false
   */
  private boolean hasErrorResult(Map<String, Object> body) {
    return body.get(BaiduOcrConstant.KEY_ERR_CODE) != null
        || body.get(BaiduOcrConstant.KEY_ERR_MSG) != null;
  }

}
