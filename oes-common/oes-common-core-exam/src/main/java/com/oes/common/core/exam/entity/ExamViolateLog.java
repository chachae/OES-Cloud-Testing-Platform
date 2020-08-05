package com.oes.common.core.exam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 考试遗违规行为日志表实体类
 *
 * @author chachae
 * @since 2020-07-15 20:18:22
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_exam_violate_log")
public class ExamViolateLog implements Serializable {

  private static final long serialVersionUID = 3696641637981445257L;

  /**
   * 主键
   */
  @TableId(type = IdType.AUTO)
  private Long violateId;

  /**
   * 学号（用户名）
   */
  private String username;

  /**
   * 真实姓名
   */
  private String fullName;
  /**
   * 试卷编号
   */
  @NotNull(message = "{required}")
  private Long paperId;
  /**
   * 违规行为
   */
  private String behaviour;
  /**
   * 违规时间
   */
  private Date violateTime;
  /**
   * 违规停留时长
   */
  private String stayTime;
  /**
   * 系统信息
   */
  @TableField("`system`")
  private String system;
  /**
   * 浏览器信息
   */
  private String browser;
  /**
   * 抓拍
   */
  private String capture;
  /**
   * 违规描述
   */
  private String description;

  /**
   * 违规行为代码
   */
  @JsonIgnore
  @TableField(exist = false)
  @NotNull(message = "{required}")
  private Integer code;


  @TableField(exist = false)
  private String paperName;
}