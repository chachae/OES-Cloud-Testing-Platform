package com.oes.server.examination.entity.system;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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

  /**
   * 学生答题主键（id）
   */
  @TableId
  private Long answerId;
  /**
   * 试题编号（id）
   */
  private Long questionId;
  /**
   * 学生答题数据
   */
  private String answerContent;
  /**
   * 学生编号（id）
   */
  private Long studentId;
  /**
   * 试卷编号（id）
   */
  private Long paperId;

}