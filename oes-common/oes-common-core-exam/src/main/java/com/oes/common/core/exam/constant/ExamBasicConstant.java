package com.oes.common.core.exam.constant;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/7/9 16:40
 */
public interface ExamBasicConstant {

  /**
   * 统计报表缓存前缀
   */
  String STATISTIC_PREFIX = "exam:basic:statistic:key";

  /**
   * 统计报表缓存时效
   */
  Long STATISTIC_EXPIRE = 3600L;

  /**
   * 单场考试最高违规次数
   */
  Integer MAX_VIOLATE_COUNT = 3;

}
