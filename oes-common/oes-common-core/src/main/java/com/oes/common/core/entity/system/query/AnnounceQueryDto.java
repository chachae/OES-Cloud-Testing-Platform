package com.oes.common.core.entity.system.query;

import com.oes.common.core.entity.QueryParam;
import lombok.Data;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/7/6 10:53
 */

@Data
public class AnnounceQueryDto extends QueryParam {

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
