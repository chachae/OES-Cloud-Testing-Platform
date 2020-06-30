package com.oes.ai.ocr.baidu;

import lombok.AllArgsConstructor;

/**
 * 一致性状态枚举
 *
 * @author chachae
 * @version v1.0
 * @date 2020/6/30 13:27
 */
@AllArgsConstructor
public enum IdCardNumTypeEnum {

  ALL_NULL(-1, "身份证正面所有字段全为空"),

  ERROR(0, "身份证证号识别错误"),

  ALL_RIGHT(1, "身份证证号和性别、出生信息一致"),

  SEX_BIRTH_ERR(2, "身份证证号和性别、出生信息都不一致"),

  NUM_BIRTH_ERR(3, "身份证证号和出生信息不一致"),

  NUM_SEX_ERR(4, "身份证证号和性别信息不一致");

  private final Integer type;

  private final String value;

  public static String getValue(Integer typeCode) {
    for (IdCardNumTypeEnum enumObj : IdCardNumTypeEnum.values()) {
      if (typeCode.equals(enumObj.type)) {
        return enumObj.value;
      }
    }
    return ERROR.value;
  }

  public static boolean isAllRight(Integer typeCode) {
    return typeCode != null && typeCode.equals(ALL_RIGHT.type);
  }
}
