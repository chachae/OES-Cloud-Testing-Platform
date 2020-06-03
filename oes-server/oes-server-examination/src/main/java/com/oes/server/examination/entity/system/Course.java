package com.oes.server.examination.entity.system;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 课程表实体类
 *
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
@Data
@TableName("t_course")
public class Course implements Serializable {

  private static final long serialVersionUID = 5832642844824442302L;

  /**
   * 课程主键（id）
   */
  @TableId
  private Long courseId;
  /**
   * 课程名称
   */
  private String courseName;
  /**
   * 所属部门
   */
  private Long deptId;
  /**
   * 创建人
   */
  private Long creatorId;
  /**
   * 创建时间
   */
  private Date createTime;
  /**
   * 更新时间
   */
  private Date updateTime;
}