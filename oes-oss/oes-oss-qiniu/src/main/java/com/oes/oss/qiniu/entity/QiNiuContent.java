package com.oes.oss.qiniu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 七牛云文件存储(QiniuContent)表实体类
 *
 * @author chachae
 * @since 2020-06-27 11:37:24
 */
@Data
@TableName("t_qiniu_content")
public class QiNiuContent implements Serializable {

  private static final long serialVersionUID = -6405048888525893118L;

  /**
   * ID
   */
  @TableId(type = IdType.AUTO)
  private Long contentId;
  /**
   * Bucket 识别符
   */
  private String bucket;
  /**
   * 文件名称
   */
  private String name;
  /**
   * 文件大小
   */
  private String size;
  /**
   * 文件类型：私有或公开
   */
  private String type;
  /**
   * 文件url
   */
  private String url;
  /**
   * 文件后缀
   */
  private String suffix;
  /**
   * 上传或同步的时间
   */
  private Date updateTime;


}