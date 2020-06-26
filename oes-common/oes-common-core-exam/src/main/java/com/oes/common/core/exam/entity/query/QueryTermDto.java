package com.oes.common.core.exam.entity.query;

import com.oes.common.core.entity.QueryParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/26 17:56
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryTermDto extends QueryParam {

  private static final long serialVersionUID = -1386337551551809169L;

  private String termName;

}
