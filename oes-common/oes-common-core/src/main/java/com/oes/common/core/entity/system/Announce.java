package com.oes.common.core.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 系统公告表(Announce)表实体类
 *
 * @author chachae
 * @since 2020-07-06 10:40:27
 */
@Data
@TableName("t_announce")
public class Announce implements Serializable {

  private static final long serialVersionUID = -1228526919492323088L;

  /**
   * 公告主键
   */
  @TableId(type = IdType.AUTO)
  private Long announceId;
  /**
   * 公告标题
   */
  @NotBlank(message = "{required}")
  private String title;
  /**
   * 公告内容编号
   */
  private Long contentId;
  /**
   * 创建人
   */
  private String creatorName;
  /**
   * 创建时间
   */
  private Date createTime;
  /**
   * 更新时间
   */
  private Date updateTime;

  /**
   * 公告状态
   */
  private Integer status;

  public static final Integer STATUS_ACTIVE = 1;

  public static final Integer STATUS_CLOSE = 0;

}