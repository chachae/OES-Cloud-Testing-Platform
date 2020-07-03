package com.oes.common.core.exam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 成绩表实体类
 *
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
@Data
@NoArgsConstructor
@TableName("t_score")
public class Score implements Serializable {

  private static final long serialVersionUID = 4708290388061842475L;

  public static final Integer DEFAULT_SCORE = 0;

  public static final Integer STATUS_HAS_SUBMIT = 1;
  public static final Integer STATUS_NOT_SUBMIT = 0;

  /**
   * 成绩编号（id）
   */
  @TableId(type = IdType.AUTO)
  private Long scoreId;
  /**
   * 成绩
   */
  @TableField("`score`")
  private Integer studentScore;
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
  private String times;
  /**
   * 创建时间
   */
  private Date createTime;

  /**
   * 更新时间
   */
  private Date updateTime;

  /**
   * 试卷提交状态（1：已提交，0：未提交）
   */
  private Integer status;

  /**
   * 试卷名称
   */
  @TableField(exist = false)
  private String paperName;

  /**
   * 试卷分数
   */
  @TableField(exist = false)
  private Integer paperScore;

  /**
   * 学生姓名
   */
  @TableField(exist = false)
  private String fullName;

  /**
   * 用户名（学号）
   */
  @TableField(exist = false)
  private String username;

  /**
   * 学期
   */
  @TableField(exist = false)
  private String termName;

  /**
   * 课程名称
   */
  @TableField(exist = false)
  private String courseName;

  /**
   * 学期
   */
  @JsonIgnore
  @TableField(exist = false)
  private Long termId;

  /**
   * 用户名或学号
   */
  @JsonIgnore
  @TableField(exist = false)
  private String key;

}