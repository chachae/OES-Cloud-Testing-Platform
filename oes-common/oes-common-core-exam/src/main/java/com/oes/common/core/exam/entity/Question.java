package com.oes.common.core.exam.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 试题表实体类
 *
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
@Data
@TableName("t_question")
public class Question implements Serializable {

  private static final long serialVersionUID = 1033860346245112691L;

  /**
   * 难度：简单
   */
  private static final Integer EASY = 1;
  /**
   * 难度：中等
   */
  private static final Integer MEDIUM = 2;
  /**
   * 难度：困难
   */
  private static final Integer HARD = 3;

  /**
   * 题目主键（id）
   */
  @TableId(type = IdType.AUTO)
  private Long questionId;
  /**
   * 题干
   */
  private String questionName;
  /**
   * 题目图片
   */
  private String questionImage;

  /**
   * 选项
   */
  private String options;

  /**
   * 正确答案
   */
  private String rightKey;

  /**
   * 试题解析
   */
  private String analysis;
  /**
   * 创建时间
   */
  @TableField(fill = FieldFill.INSERT)
  private Date createTime;
  /**
   * 更新时间
   */
  @TableField(fill = FieldFill.UPDATE)
  private Date updateTime;
  /**
   * 创建人用户编号
   */
  private Long creatorId;
  /**
   * 出题人姓名
   */
  private String creatorName;
  /**
   * 题目类型
   */
  private Long typeId;
  /**
   * 所属课程
   */
  private Long courseId;
  /**
   * 试题难度
   */
  private Integer difficult;
  /**
   * 使用量
   */
  private Integer consumption;

  /**
   * 题目类型名称
   */
  @TableField(exist = false)
  private String typeName;

  /**
   * 课程名称
   */
  @TableField(exist = false)
  private String courseName;
}