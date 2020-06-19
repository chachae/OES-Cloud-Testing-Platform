package com.oes.common.core.entity.exam;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.Size;
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

  public static final String[] DEFAULT_TYPE_ID_ARRAY = {"1", "2", "3", "4", "5"};

  /**
   * 题目类型主键
   */
  @TableId(type = IdType.AUTO)
  private Long typeId;
  /**
   * 类型名称
   */
  @Size(min = 2, max = 10, message = "{range}")
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