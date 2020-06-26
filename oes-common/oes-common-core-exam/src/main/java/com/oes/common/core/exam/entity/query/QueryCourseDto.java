package com.oes.common.core.exam.entity.query;

import com.oes.common.core.entity.QueryParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/26 17:46
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryCourseDto extends QueryParam {

  private static final long serialVersionUID = 3513584149110006010L;

  /**
   * 课程名称
   */
  private String courseName;

  /**
   * 部门编号
   */
  private Long deptId;

  /**
   * 创建者编号
   */
  private Long creatorId;

}
