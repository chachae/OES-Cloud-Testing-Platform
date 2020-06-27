package com.oes.oss.qiniu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 七牛云配置(QiniuConfig)表实体类
 *
 * @author chachae
 * @since 2020-06-27 11:37:18
 */
@Data
@TableName("t_qiniu_config")
public class QiNiuConfig implements Serializable {

  private static final long serialVersionUID = -1387173587113205878L;

  /**
   * ID
   */
  @TableId(type = IdType.INPUT)
  private Long configId;
  /**
   * accessKey
   */
  private String accessKey;
  /**
   * Bucket 识别符
   */
  private String bucket;
  /**
   * 外链域名
   */
  private String host;
  /**
   * secretKey
   */
  private String secretKey;
  /**
   * 空间类型
   */
  private String type;
  /**
   * 机房
   */
  private String zone;


}