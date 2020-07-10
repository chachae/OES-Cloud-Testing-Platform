package com.oes.ai.entity.face;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/7/10 09:45
 */
@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class FaceMatchInfo implements Serializable {

  private static final long serialVersionUID = 2003868341919562181L;

  private Integer score;

  private String faceTokenA;

  private String faceTokenB;

  public FaceMatchInfo(Integer score) {
    this.score = score;
  }

}
