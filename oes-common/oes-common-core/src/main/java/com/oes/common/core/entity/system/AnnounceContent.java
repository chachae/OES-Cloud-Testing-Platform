package com.oes.common.core.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 系统公告内容表(AnnounceContent)表实体类
 *
 * @author chachae
 * @since 2020-07-06 17:42:33
 */
@Data
@TableName("t_announce_content")
public class AnnounceContent implements Serializable {

  private static final long serialVersionUID = -1258559919492323271L;

  /**
   * 公告内容主键
   */
  @TableId(type = IdType.AUTO)
  private Long contentId;

  /**
   * 公告内容
   */
  private String htmlContent;

}