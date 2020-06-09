package com.oes.server.examination.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import lombok.Data;

/**
 * 试卷-问题中间表实体类
 *
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
@Data
@TableName("t_paper_question")
@JsonInclude(Include.NON_NULL)
public class PaperQuestion implements Serializable {

  private static final long serialVersionUID = 516987580634546721L;

  /**
   * 试卷编号（id）
   */
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
  /**
   * 选项A
   */
  @TableField(exist = false)
  private String optionA;
  /**
   * 选项B
   */
  @TableField(exist = false)
  private String optionB;
  /**
   * 选项C
   */
  @TableField(exist = false)
  private String optionC;
  /**
   * 选项D
   */
  @TableField(exist = false)
  private String optionD;
  /**
   * 选项E
   */
  @TableField(exist = false)
  private String optionE;
  /**
   * 选项F
   */
  @TableField(exist = false)
  private String optionF;
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


}