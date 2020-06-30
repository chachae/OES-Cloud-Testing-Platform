package com.oes.ai.ocr.baidu.entity.query;

import java.io.Serializable;
import lombok.Data;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/29 16:44
 */
@Data
public class QueryBaiduOcrDto implements Serializable {

  private static final long serialVersionUID = -2339884287648975012L;

  private String image;

  private String idCardSide;

}
