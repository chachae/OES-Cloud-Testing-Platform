package com.oes.oss.qiniu.entity.query;

import com.oes.common.core.entity.QueryParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/27 13:19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QiNiuQueryDto extends QueryParam {

  private static final long serialVersionUID = 1438987278972535723L;

  private String key;

}
