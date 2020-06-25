package com.oes.common.core.exam.entity;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/10 23:44
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;


@Data
@TableName("t_term")
public class Term implements Serializable {

  private static final long serialVersionUID = 2079088244461567946L;

  /**
   * 学期主键
   */
  @TableId(type = IdType.AUTO)
  private Long termId;

  /**
   * 学期名称
   */
  private String termName;

  /**
   * 创建时间
   */
  private Date createTime;

  /**
   * 更新时间
   */
  private Date updateTime;

}
