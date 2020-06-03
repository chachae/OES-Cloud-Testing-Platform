package com.oes.server.examination.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 试卷-试题类型中间表实体类
 *
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
@Data
@TableName("t_paper_type")
public class PaperType implements Serializable {

  private static final long serialVersionUID = 8924872045167097154L;

  /**
   * 模板主键
   */
  private Long paperId;
  /**
   * 试题类型编号（id）
   */
  private Long typeId;
  /**
   * 题目分值
   */
  private Integer score;

}