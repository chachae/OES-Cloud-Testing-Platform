package com.oes.common.core.exam.entity.query;

import com.oes.common.core.entity.QueryParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/26 17:53
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryQuestionDto extends QueryParam {

  private static final long serialVersionUID = 8163923348455117161L;

  /**
   * 题干
   */
  private String questionName;

  /**
   * 课程
   */
  private Long courseId;

  /**
   * 类型
   */
  private Long typeId;
}
