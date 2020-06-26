package com.oes.common.core.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页参数
 *
 * @author chachae
 * @since 2020/4/30 20:15
 */
@Data
@NoArgsConstructor
public class QueryParam implements Serializable {

  private static final long serialVersionUID = -4987408107332328482L;

  public QueryParam(Long pageNum, Long pageSize) {
    this.pageNum = pageNum;
    this.pageSize = pageSize;
  }

  /**
   * 页号，默认第1页
   */
  protected Long pageNum = 1L;

  /**
   * 每页数据量，默认10条
   */
  protected Long pageSize = 10L;

  /**
   * 排序字段
   */
  protected String field;

  /**
   * 排序规则，asc升序，desc降序
   */
  protected String order;

}
