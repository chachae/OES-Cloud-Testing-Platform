package com.oes.common.core.entity.examination;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 试卷-试题类型中间表实体类
 *
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
@Data
@NoArgsConstructor
@TableName("t_paper_type")
public class PaperType implements Serializable {

  private static final long serialVersionUID = 8924872045167097154L;

  public PaperType(Long paperId, Long typeId, Integer score, Integer num) {
    this.paperId = paperId;
    this.typeId = typeId;
    this.score = score;
    this.num = num;
  }

  /**
   * 模板主键
   */
  @TableId(type = IdType.INPUT)
  private Long paperId;
  /**
   * 试题类型编号（id）
   */
  @TableId(type = IdType.INPUT)
  private Long typeId;
  /**
   * 题目分值
   */
  private Integer score;
  /**
   * 题目数量
   */
  private Integer num;

  /**
   * 试题类型编号（id）
   */
  @JsonIgnore
  private String typeIds;
  /**
   * 题目分值
   */
  @JsonIgnore
  private String scores;
  /**
   * 题目数量
   */
  @JsonIgnore
  private String nums;

  /**
   * 题目难度
   */
  private Integer difficult;

}