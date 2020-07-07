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
public class QueryAnswerDto extends QueryParam {

  private static final long serialVersionUID = -4631054172498714743L;

  private Long paperId;

  private String paperName;

  private String deptName;

  private Long termId;

  private Long studentId;

  private Integer status;

  private String studentName;

  private Integer warn;

}
