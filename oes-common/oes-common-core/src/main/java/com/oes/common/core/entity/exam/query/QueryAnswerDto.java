package com.oes.common.core.entity.exam.query;

import lombok.Data;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/21 18:14
 */
@Data
public class QueryAnswerDto {

  private String paperName;

  private String deptName;

  private Long termId;

  private Long status;

  private String studentName;

}
