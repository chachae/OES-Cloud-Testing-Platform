package com.oes.common.core.exam.entity.query;

import com.oes.common.core.entity.QueryParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/26 17:36
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryTypeDto extends QueryParam {

  private static final long serialVersionUID = 1693200750317569131L;

  /**
   * 类型名称
   */
  private String typeName;

}
