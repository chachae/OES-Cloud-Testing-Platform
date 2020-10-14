package com.oes.common.core.exam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.oes.common.core.validator.Update;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 问题表实体类
 *
 * @author chachae
 * @since 2020-06-03 16:43:11
 */
@Data
@TableName("t_answer")
public class Answer implements Serializable {

  private static final long serialVersionUID = -2379218847487357896L;

  public static final Integer STATUS_CORRECT = 1;

  public static final Integer STATUS_NOT_CORRECT = 0;

  public static final Integer IS_RIGHT = 1;

  public static final Integer IS_WARN = 0;

  /**
   * 学生答题主键（id）
   */
  @TableId(type = IdType.AUTO)
  // 更新的时候才校验
  @NotNull(message = "{required}", groups = {Update.class})
  private Long answerId;
  /**
   * 学生答题数据
   */
  private String answerContent;
  /**
   * 用户名
   */
  private String username;
  /**
   * 试卷编号（id）
   */
  @NotNull(message = "{required}")
  private Long paperId;
  /**
   * 试题编号（id）
   */
  private Long questionId;
  /**
   * 题目的分
   */
  @NotNull(message = "{required}")
  private Integer score;
  /**
   * 创建时间
   */
  private Date createTime;
  /**
   * 更新时间
   */
  private Date updateTime;
  /**
   * 答案状态（1：批改，0：未批改）
   */
  private Integer status;

  /**
   * 是否正确（1：回答正确，0：回答错误）
   */
  private Integer warn;
  /**
   * 试卷名称
   */
  @TableField(exist = false)
  private String paperName;
  /**
   * 题干
   */
  @TableField(exist = false)
  private String questionName;
  /**
   * 题目类型编号
   */
  @TableField(exist = false)
  private Integer typeId;
  /**
   * 选项
   */
  @TableField(exist = false)
  private String options;
  /**
   * 正确答案
   */
  @TableField(exist = false)
  private String rightKey;
  /**
   * 解析
   */
  @TableField(exist = false)
  private String analysis;
  /**
   * 学期名称
   */
  @TableField(exist = false)
  private String termName;
}