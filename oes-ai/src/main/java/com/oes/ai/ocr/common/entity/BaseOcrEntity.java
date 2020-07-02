package com.oes.ai.ocr.common.entity;

import com.oes.ai.ocr.aliyun.constant.AliyunOcrConstant;
import com.oes.ai.ocr.baidu.constant.BaiduOcrConstant;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/30 11:08
 */
@Data
public class BaseOcrEntity implements Serializable {

  private static final long serialVersionUID = 9141477772203967657L;

  private String image;

  private transient MultipartFile file;

  @NotBlank(message = "{required}")
  private String side = BaiduOcrConstant.SIDE_FRONT;

  @NotBlank(message = "{required}")
  private String type = AliyunOcrConstant.TYPE_OF_FACE;

}
