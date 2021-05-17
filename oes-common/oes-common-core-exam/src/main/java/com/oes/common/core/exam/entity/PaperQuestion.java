package com.oes.common.core.exam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 试卷-问题中间表实体类
 *
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
@Data
@NoArgsConstructor
@TableName("t_paper_question")
@JsonInclude(Include.NON_NULL)
public class PaperQuestion implements Serializable {

  private static final long serialVersionUID = 516987580634546721L;

  public PaperQuestion(Long paperId, Long questionId) {
    this.paperId = paperId;
    this.questionId = questionId;
  }

  /**
   * 试卷编号（id）
   */
  @TableId(type = IdType.INPUT)
  private Long paperId;
  /**
   * 试题编号（id)
   */
  private Long questionId;
  /**
   * 试题名称
   */
  @TableField(exist = false)
  private String questionName;
  /**
   * 试题类型
   */
  @TableField(exist = false)
  private Long typeId;

  @TableField(exist = false)
  private String options;

  @TableField(exist = false)
  private Integer fillCount;

  /**
   * 正确答案
   */
  @TableField(exist = false)
  private String rightKey;
  /**
   * 题目解析
   */
  @TableField(exist = false)
  private String analysis;

  /**
   * 学生答案编号
   */
  @TableField(exist = false)
  private Long answerId;

  /**
   * 学生答案
   */
  @TableField(exist = false)
  private String answerContent;

  /**
   * 分值
   */
  @TableField(exist = false)
  private Integer score;
}