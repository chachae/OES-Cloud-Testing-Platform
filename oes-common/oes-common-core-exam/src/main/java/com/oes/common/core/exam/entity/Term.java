package com.oes.common.core.exam.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/10 23:44
 */
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
  @TableField(fill = FieldFill.INSERT)
  private Date createTime;

  /**
   * 更新时间
   */
  @TableField(fill = FieldFill.UPDATE)
  private Date updateTime;

}
