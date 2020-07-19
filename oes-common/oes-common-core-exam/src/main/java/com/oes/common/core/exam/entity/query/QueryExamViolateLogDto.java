package com.oes.common.core.exam.entity.query;

import com.oes.common.core.entity.QueryParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/21 18:14
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryExamViolateLogDto extends QueryParam {

  private static final long serialVersionUID = 1519286134836197385L;

  private Long paperId;

  private String paperName;

  private Long termId;

  private String username;

  private String fullName;

}
