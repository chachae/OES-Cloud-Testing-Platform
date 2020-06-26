package com.oes.common.core.exam.entity.query;

import com.oes.common.core.entity.QueryParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/26 17:49
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryPaperDto extends QueryParam {

  private static final long serialVersionUID = 3863446215830044928L;

  /**
   * 试卷名称
   */
  private String paperName;

  /**
   * 学期
   */
  private String termId;
}
