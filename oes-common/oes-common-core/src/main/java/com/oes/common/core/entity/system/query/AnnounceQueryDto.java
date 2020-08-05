package com.oes.common.core.entity.system.query;

import com.oes.common.core.entity.QueryParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/7/6 10:53
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class AnnounceQueryDto extends QueryParam {

  private static final long serialVersionUID = 830930215315431455L;

  /**
   * 查询关键字
   */
  private String key;

  /**
   * 创建人姓名
   */
  private String creatorName;

  /**
   * 公告状态
   */
  private Integer status;

}
