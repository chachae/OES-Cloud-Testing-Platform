package com.oes.ai.function.supplier.baidu;

import lombok.AllArgsConstructor;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/7/10 10:05
 */

@AllArgsConstructor
public enum CommonErrorEnum {

  PARAM_IS_NULL(222001, "必要参数未传入"),

  PARAM_FORMAT_ERR(222002, "必要参数未传入"),

  NET_NOT_AVALIABLE(222201, "服务端请求失败"),

  NO_HAS_FACE(222202, "图片中没有人脸"),

  FACE_CHECK_FAIL(222203, "无法解析人脸"),

  FACE_TOKEN_NOT_EXIST(222209, "face token 不存在"),

  FACE_IS_COVERED(223113, "人脸有被遮挡"),

  FACE_IS_FUZZY(223114, "人脸模糊"),

  FACE_LIGHT_IS_NOT_GOOD(223115, "人脸光照不好"),

  FACE_INCOMPLETE(223116, "人脸不完整"),

  QUALITY_CONTROL_ERR(223118, "质量控制项错误"),

  LIVENESS_CONTROL_ITEM_ERROR(223119, "活体控制项错误"),

  LIVENESS_CHECK_FAIL(223120, "活体检测未通过"),

  LEFT_EYE_IS_OCCLUSION(223121, "质量检测未通过 左眼遮挡程度过高"),

  RIGHT_EYE_IS_OCCLUSION(223122, "质量检测未通过 右眼遮挡程度过高"),

  LEFT_CHEEK_IS_OCCLUSION(223123, "质量检测未通过 左脸遮挡程度过高"),

  RIGHT_CHEEK_IS_OCCLUSION(223124, "质量检测未通过 右脸遮挡程度过高"),

  CHIN_CONTOUR_IS_OCCLUSION(223125, "质量检测未通过 下巴遮挡程度过高"),

  NOSE_IS_OCCLUSION(223126, "质量检测未通过 鼻子遮挡程度过高"),

  MOUTH_IS_OCCLUSION(223127, "质量检测未通过 嘴巴遮挡程度过高"),

  UNKNOW(100001, "未知异常");

  private final Integer errorCode;

  private final String errorMsg;

  public static String getErrorMsg(Integer errorCode) {
    for (CommonErrorEnum curEnum : CommonErrorEnum.values()) {
      if (curEnum.errorCode.equals(errorCode)) {
        return curEnum.errorMsg;
      }
    }
    return UNKNOW.errorMsg;
  }

}
