package com.oes.common.core.util;

import cn.hutool.core.util.StrUtil;

/**
 * 数据脱敏工具
 *
 * @author chachae
 * @version v1.0
 * @date 2020/7/1 21:52
 */
public class DimUtil {

  private DimUtil() {
  }

  private static final String DIM_STAR_1 = "*";
  private static final String DIM_STAR_2 = "**";
  private static final String DIM_STAR_4 = "****";
  private static final String DIM_STAR_8 = "********";

  /**
   * 姓名
   */
  public static String fullName(String fullName) {
    if (StrUtil.isBlank(fullName)) {
      return "";
    }

    if (fullName.length() == 3) {
      return fullName.charAt(0) + DIM_STAR_1 + fullName.substring(2);
    }

    if (fullName.length() == 2) {
      return fullName.charAt(0) + DIM_STAR_1;
    }

    if (fullName.length() > 3) {
      return fullName.charAt(0) + DIM_STAR_2 + fullName.substring(3);
    }

    return "*";
  }

  /**
   * 手机号
   * <pre>
   *  136****6666
   * </pre>
   */
  public static String mobile(String mobile) {
    if (StrUtil.isBlank(mobile)) {
      return "";
    }

    if (mobile.length() >= 11) {
      return mobile.substring(0, 3) + DIM_STAR_4 + mobile.substring(7);
    }

    return mobile;
  }

  /**
   * 身份证
   * <pre>
   *  4405********280137
   * </pre>
   */
  public static String idCard(String idCard) {
    if (StrUtil.isBlank(idCard)) {
      return "";
    }

    // 二代身份证
    if (idCard.length() == 18) {
      return idCard.substring(0, 4) + DIM_STAR_8 + idCard.substring(12);
    }

    // 一代身份证
    if (idCard.length() == 15) {
      return idCard.substring(0, 3) + DIM_STAR_8 + idCard.substring(11);
    }

    return "";
  }

}
