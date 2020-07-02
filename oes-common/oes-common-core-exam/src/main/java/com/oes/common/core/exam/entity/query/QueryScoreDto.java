package com.oes.common.core.exam.entity.query;

import com.oes.common.core.entity.QueryParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分数查询条件实体类
 *
 * @author chachae
 * @version v1.0
 * @date 2020/6/21 11:02
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryScoreDto extends QueryParam {

  private static final long serialVersionUID = -4013485999672960436L;

  /**
   * 学期
   */
  private Long termId;

  /**
   * 用户名或学号
   */
  private String key;

  /**
   * 试卷编号
   */
  private Long paperId;

  /**
   * 学生编号
   */
  private Long studentId;

  /**
   * 部门名称
   */
  private String deptName;

  /**
   * 成绩状态
   */
  private Integer status;

  /**
   * 试卷名称
   */
  private String paperName;

}
