package com.oes.common.core.entity.examination;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 成绩表实体类
 *
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
@Data
@TableName("t_score")
public class Score implements Serializable {

  private static final long serialVersionUID = 4708290388061842475L;
  /**
   * 成绩编号（id）
   */
  @TableId(type = IdType.AUTO)
  private Long scoreId;
  /**
   * 成绩
   */
  private Integer score;
  /**
   * 试卷编号（id）
   */
  private Long paperId;
  /**
   * 学生编号（id）
   */
  private Long studentId;
  /**
   * 考试用时
   */
  private String time;

}