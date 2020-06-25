package com.oes.common.core.exam.entity;

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
   * 选项A
   */
  private String optionA;
  /**
   * 选项B
   */
  private String optionB;
  /**
   * 选项C
   */
  private String optionC;
  /**
   * 选项D
   */
  private String optionD;
  /**
   * 选项E
   */
  private String optionE;
  /**
   * 选项F
   */
  private String optionF;
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
  private Date createTime;
  /**
   * 更新时间
   */
  private Date updateTime;
  /**
   * 创建人用户编号
   */
  private Long creatorId;
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
   * 出题人姓名
   */
  @TableField(exist = false)
  private String fullName;

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