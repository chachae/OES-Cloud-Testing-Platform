package com.oes.common.core.entity;

import com.oes.common.core.entity.system.Menu;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/5/27 11:25
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuTree extends Tree<Menu> {

  private String path;
  private String component;
  private String perms;
  private String icon;
  private String type;
  private Integer orderNum;
}
