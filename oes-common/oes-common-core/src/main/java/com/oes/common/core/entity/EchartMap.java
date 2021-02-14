package com.oes.common.core.entity;

import java.util.HashMap;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * EChart 数据体
 *
 * @author chachae
 * @version v1.0
 * @date 2020/7/21 18:04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EchartMap extends HashMap<String, Object> {

  private static final long serialVersionUID = -8422981954211556220L;

  public EchartMap putData(String name, Object value) {
    return this.putName(name).putValue(value);
  }

  public EchartMap putName(Object name) {
    this.put("name", name);
    return this;
  }

  public EchartMap putValue(Object value) {
    this.put("value", value);
    return this;
  }
}
