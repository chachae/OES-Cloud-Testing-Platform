package com.oes.common.core.constant;

/**
 * 常用正则表达式常量
 *
 * @author chachae
 * @since 2020/04/30 14:21
 */
public interface RegexpConstant {

  /**
   * 移动电话（国内）
   */
  String MOBILE_REG = "^(?:(?:\\+|00)86)?1[3-9]\\d{9}$";

  /**
   * 身份证（1代 / 2代）
   */
  String ID_CARD_REG = "(^\\d{8}(0\\d|10|11|12)([0-2]\\d|30|31)\\d{3}$)|(^\\d{6}(18|19|20)\\d{2}(0[1-9]|10|11|12)([0-2]\\d|30|31)\\d{3}(\\d|X|x)$)";

  /**
   * 包含中文
   */
  String CONTAINS_CHINESE = "^[\\u4E00-\\u9FA5]+$";
}
