package com.oes.ai.function.ocr;

import lombok.AllArgsConstructor;

/**
 * OCR厂商枚举
 *
 * @author chachae
 * @version v1.0
 * @date 2020/6/30 12:17
 */
@AllArgsConstructor
public enum SupplierEnum {

  ALIYUN("aliyun", "aliyunOcrIdCardServiceImpl"),

  BAIDU("baidu", "baiduOcrIdCardServiceImpl");

  private final String supplier;

  private final String beanName;

  public static String getBeanName(String supplier) {
    for (SupplierEnum enumObj : SupplierEnum.values()) {
      if (supplier.equals(enumObj.supplier)) {
        return enumObj.beanName;
      }
    }
    return BAIDU.beanName;
  }

}
