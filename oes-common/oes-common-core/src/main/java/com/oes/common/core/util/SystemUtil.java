package com.oes.common.core.util;

import cn.hutool.core.util.StrUtil;
import com.google.common.base.CaseFormat;
import java.time.LocalDateTime;
import org.springframework.core.env.Environment;

/**
 * 系统常用工具
 *
 * @author chachae
 * @since 2020/5/12 15:21
 */
public class SystemUtil {

  private SystemUtil() {
  }

  /**
   * 系统启动成功信息答应
   *
   * @param environment /
   */
  public static void printServerUpBanner(Environment environment) {
    String banner = "-----------------------------------------\n" +
        "服务启动成功，时间：" + DateUtil
        .formatFullTime(LocalDateTime.now(), DateUtil.FULL_TIME_SPLIT_PATTERN) + "\n" +
        "服务名称：" + environment.getProperty("spring.application.name") + "\n" +
        "端口号：" + environment.getProperty("server.port") + "\n" +
        "-----------------------------------------";
    System.out.println(banner);
  }

  /**
   * 驼峰转下划线
   *
   * @param value 待转换值
   * @return 结果
   */
  public static String camelToUnderscore(String value) {
    if (StrUtil.isBlank(value)) {
      return value;
    }
    return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, value);
  }

  /**
   * 下划线转驼峰
   *
   * @param value 待转换值
   * @return 结果
   */
  public static String underscoreToCamel(String value) {
    if (StrUtil.isBlank(value)) {
      return value;
    }
    return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, value);
  }

}
