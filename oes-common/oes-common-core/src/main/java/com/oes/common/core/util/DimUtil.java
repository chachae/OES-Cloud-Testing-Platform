package com.oes.common.core.util;

import org.apache.commons.lang3.StringUtils;

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

  private static final String OVERLAY = "****";
  private static final String OVERLAY_DOUBLE = "********";

  /**
   * 姓名
   */
  public static String fullName(String fullName) {
    if (StringUtils.isBlank(fullName)) {
      return "";
    }

    if (fullName.length() >= 3) {
      return StringUtils.overlay(fullName, "**", 1, 3);
    }

    if (fullName.length() == 2) {
      return StringUtils.overlay(fullName, "*", 1, 2);
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
    if (StringUtils.isBlank(mobile)) {
      return "";
    }

    return StringUtils.overlay(mobile, OVERLAY, 3, 7);
  }

  /**
   * 身份证
   * <pre>
   *  4405********280137
   * </pre>
   */
  public static String idCard(String idCard) {
    if (StringUtils.isBlank(idCard)) {
      return "";
    }

    // 二代身份证
    if (idCard.length() == 18) {
      return StringUtils.overlay(idCard, OVERLAY_DOUBLE, 4, 12);
    }

    // 一代身份证
    if (idCard.length() == 15) {
      return StringUtils.overlay(idCard, OVERLAY_DOUBLE, 3, 11);
    }

    return "";
  }
}
