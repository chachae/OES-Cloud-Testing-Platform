package com.oes.common.core.exam.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.oes.common.core.entity.EchartMap;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/7/21 17:47
 */
@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class StatisticScoreVo implements Serializable {

  private static final long serialVersionUID = -1071113046171279024L;

  private Integer max;

  private Integer min;

  private Double average;

  /**
   * 班级（部门）排名
   */
  private Integer rank;

  /**
   * 有效成绩数量
   */
  private Integer scoreCount;

  /**
   * 班级（部门）人数
   */
  private Integer fullCount;

  /**
   * 分数段统计
   */
  private List<EchartMap> fraction;

  public StatisticScoreVo(Integer max, Integer min, Double average, List<EchartMap> fraction) {
    this.max = max;
    this.min = min;
    this.average = average;
    this.fraction = fraction;
  }
}
