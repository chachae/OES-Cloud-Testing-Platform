package com.oes.ai.function.ocr.baidu.util;

import com.oes.ai.entity.ocr.IdCardInfo;
import com.oes.ai.function.ocr.baidu.constant.BaiduOcrConstant;
import com.oes.common.core.constant.SystemConstant;
import java.util.Map;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/30 01:14
 */
public class BaiduIdCardResultUtil {

  private BaiduIdCardResultUtil() {
  }

  public static IdCardInfo mapToIdCardInfo(String side, Map<String, Object> result) {
    if (side.equals(BaiduOcrConstant.SIDE_FRONT)) {
      return faceSideToIdCardInfo(result);
    } else {
      return backSideToIdCardInfo(result);
    }
  }

  @SuppressWarnings("unchecked")
  private static IdCardInfo faceSideToIdCardInfo(Map<String, Object> result) {

    IdCardInfo info = new IdCardInfo();
    Object obj = result.get(BaiduOcrConstant.KEY_WORDS_RESULT);

    if (obj instanceof Map) {

      Map<String, Object> map = (Map<String, Object>) obj;

      // 住址
      info.setAddress(((Map<String, Object>) map.get(BaiduOcrConstant.KEY_ADDRESS))
          .get(BaiduOcrConstant.KEY_WORDS) + "");

      // 出事日期
      info.setBirth(((Map<String, Object>) map.get(BaiduOcrConstant.KEY_BIRTH)).get(
          BaiduOcrConstant.KEY_WORDS) + "");

      // 姓名
      info.setName(((Map<String, Object>) map.get(BaiduOcrConstant.KEY_NAME)).get(
          BaiduOcrConstant.KEY_WORDS) + "");

      // 身份证号码
      info.setNum(((Map<String, Object>) map.get(BaiduOcrConstant.KEY_NUM)).get(
          BaiduOcrConstant.KEY_WORDS) + "");

      // 性别
      info.setSex(((Map<String, Object>) map.get(BaiduOcrConstant.KEY_SEX)).get(
          BaiduOcrConstant.KEY_WORDS) + "");

      // 民族
      info.setNationality(((Map<String, Object>) map.get(BaiduOcrConstant.KEY_NATIONALITY)).get(
          BaiduOcrConstant.KEY_WORDS) + "");

      // 头像base64
      String photoBase64Str =
          SystemConstant.BASE64_JPG_HEAD + result.get(BaiduOcrConstant.KEY_PHOTO);

      // 是否识别成功
      info.setSuccess(true);

      // 是否为伪造复印件
      info.setIsFake(
          !result.get(BaiduOcrConstant.KEY_FAKE).equals(BaiduOcrConstant.STATUS_NORMAL));
      info.setPhoto(photoBase64Str);
    }

    return info;
  }

  @SuppressWarnings("unchecked")
  private static IdCardInfo backSideToIdCardInfo(Map<String, Object> result) {

    IdCardInfo info = new IdCardInfo();
    Object obj = result.get(BaiduOcrConstant.KEY_WORDS_RESULT);

    if (obj instanceof Map) {

      Map<String, Object> map = (Map<String, Object>) obj;

      // 失效日期
      info.setEndDate(((Map<String, Object>) map.get(BaiduOcrConstant.EXPIRE_TIME))
          .get(BaiduOcrConstant.KEY_WORDS) + "");

      // 签发日期
      info.setStartDate(((Map<String, Object>) map.get(BaiduOcrConstant.START_TIME))
          .get(BaiduOcrConstant.KEY_WORDS) + "");

      // 签发机关
      info.setIssue(((Map<String, Object>) map.get(BaiduOcrConstant.ISSUE))
          .get(BaiduOcrConstant.KEY_WORDS) + "");

      // 是否识别成功
      info.setSuccess(true);

      // 是否为伪造复印件
      info.setIsFake(
          !result.get(BaiduOcrConstant.KEY_FAKE).equals(BaiduOcrConstant.STATUS_NORMAL));
    }

    return info;
  }
}