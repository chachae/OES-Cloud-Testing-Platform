package com.oes.ai.ocr.baidu;

import lombok.AllArgsConstructor;

/**
 * 识别状态枚举
 *
 * @author chachae
 * @version v1.0
 * @date 2020/6/30 12:17
 */
@AllArgsConstructor
public enum IdCardStatusEnum {

  NORMAL("normal", "识别正常"),

  REVERSED("reversed_side", "身份证正反面颠倒"),

  NON_IDCARD("non_idcard", "上传的图片中不包含身份证"),

  BLURRED("blurred", "身份证模糊"),

  OTHER_TYPE("other_type_card", "其他类型证照"),

  OVER_EXPOSURE("over_exposure", "身份证关键字段反光或过曝"),

  OVER_DARK("over_dark", "身份证欠曝（亮度过低）"),

  UNKNOWN("unknown", "未知状态");

  private final String key;

  private final String value;

  public static String getValue(String key) {
    for (IdCardStatusEnum enumObj : IdCardStatusEnum.values()) {
      if (key.equals(enumObj.key)) {
        return enumObj.value;
      }
    }
    return UNKNOWN.value;
  }

  public static boolean isNormal(String key) {
    return key.equals(NORMAL.key);
  }

}
