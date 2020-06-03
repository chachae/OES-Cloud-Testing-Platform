package com.oes.server.examination.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 试题表实体类
 *
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
@Data
@TableName("t_type")
public class Type implements Serializable {

  private static final long serialVersionUID = -4973257667419556920L;

  /**
   * 题目类型主键
   */
  private Long typeId;
  /**
   * 类型名称
   */
  private String typeName;
  /**
   * 题目分值
   */
  private Integer score;
  /**
   * 创建时间
   */
  private Date createTime;
  /**
   * 更新时间
   */
  private Date updateTime;

}