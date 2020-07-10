package com.oes.ai.entity.face;

import com.oes.ai.function.face.badiu.constant.BaiduFaceMatchConstant;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/7/9 21:36
 */
@Data
public class QueryFaceMatch implements Serializable {

  private static final long serialVersionUID = -8485334593503969719L;

  @NotBlank(message = "{required}")
  private String image;

  /**
   * 图片类型，默认Base64
   */
  @NotBlank(message = "{required}")
  private String imageType = BaiduFaceMatchConstant.TYPE_IMAGE_BASE64;

  private String faceType;

  private String qualityControl;

  private String livenessControl;

}
