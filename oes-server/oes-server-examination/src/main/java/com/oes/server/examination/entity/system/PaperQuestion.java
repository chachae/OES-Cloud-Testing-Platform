package com.oes.server.examination.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
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

}