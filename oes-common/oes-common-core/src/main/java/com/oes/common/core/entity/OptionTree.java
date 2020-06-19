package com.oes.common.core.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/4 21:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OptionTree<T> implements Serializable {

  private static final long serialVersionUID = -6278719841122418840L;

  private Long value;

  private String label;

  private List<OptionTree<T>> children;

  public OptionTree(Long value, String label) {
    this.value = value;
    this.label = label;
  }

}
