package com.oes.common.core.entity.exam.query;

import lombok.Data;

/**
 * 分数查询条件实体类
 *
 * @author chachae
 * @version v1.0
 * @date 2020/6/21 11:02
 */
@Data
public class QueryScoreDto {

  /**
   * 学期
   */
  private Long termId;

  /**
   * 用户名或学号
   */
  private String key;

}
