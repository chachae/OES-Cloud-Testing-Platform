package com.oes.server.examination.entity.system;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 试卷表实体类
 *
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
@Data
@TableName("t_paper")
public class Paper implements Serializable {

  private static final long serialVersionUID = 1872442241203732242L;

  /**
   * 试卷主键（id）
   */
  @TableId
  private Long paperId;
  /**
   * 试卷名称
   */
  private String paperName;
  /**
   * 开始时间
   */
  private Date startTime;
  /**
   * 结束时间
   */
  private Date endTime;
  /**
   * 创建人编号（id）
   */
  private Long creatorId;
  /**
   * 创建时间
   */
  private Date createTime;
  /**
   * 试卷状态（1：启用，0：禁用）
   */
  private Integer status;
  /**
   * 课程编号（id）
   */
  private Long courseId;

}