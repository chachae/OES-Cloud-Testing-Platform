package com.oes.common.core.entity.exam;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 试卷表实体类
 *
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
@Data
@NoArgsConstructor
@TableName("t_paper")
public class Paper implements Serializable {

  private static final long serialVersionUID = 1872442241203732242L;

  public Paper(Long paperId, Integer status, Date updateTime) {
    this.paperId = paperId;
    this.status = status;
    this.updateTime = updateTime;
  }

  /**
   * 是否随机
   */
  public static final Integer IS_RANDOM = 1;
  public static final Integer NOT_RANDOM = 0;
  /**
   * 试卷类型
   */
  public static final Integer TYPE_NORMAL = 1;
  public static final Integer TYPE_IMITATE = 0;
  /**
   * 试卷状态
   */
  public static final Integer STATUS_OPEN = 1;
  public static final Integer STATUS_CLOSE = 0;

  /**
   * 试卷主键（id）
   */
  @TableId(type = IdType.AUTO)
  private Long paperId;

  /**
   * 试卷名称
   */
  private String paperName;

  /**
   * 试卷分值
   */
  private Integer paperScore;

  /**
   * 开始时间
   */
  private Date startTime;

  /**
   * 结束时间
   */
  private Date endTime;

  /**
   * 考试时长
   */
  private Integer minute;

  /**
   * 创建人编号（id）
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


  /**
   * 试卷状态（1：启用，0：禁用）
   */
  @TableField("`status`")
  private Integer status;

  /**
   * 课程编号（id）
   */
  private Long courseId;

  /**
   * 是否为随机生成的试卷（1：是，0：否）
   */
  private Integer isRandom;

  /**
   * 试卷类型（1：正式，0：模拟考试）
   */
  private Integer type;
  /**
   * 学期编号
   */
  private Long termId;
  /**
   * 学期名称
   */
  @TableField(exist = false)
  private String termName;
  /**
   * 课程名称
   */
  @TableField(exist = false)
  private String courseName;

  /**
   * 课程班所属学院
   */
  @TableField(exist = false)
  private String deptName;

  /**
   * 考试班级集合
   */
  @TableField(exist = false)
  private String deptIds;

  /**
   * 考试班级名称集合
   */
  @TableField(exist = false)
  private String deptNames;

  /**
   * 出卷人姓名
   */
  @TableField(exist = false)
  private String creatorName;

  /**
   * 试卷题目信息（返回给前端）
   */
  @TableField(exist = false)
  private List<Map<String, Object>> paperQuestions;

  /**
   * 试卷题目信息（SQL 查询映射时使用）
   */
  @JsonIgnore
  @TableField(exist = false)
  private List<PaperQuestion> paperQuestionList;

  /**
   * 开始时间选择器
   */
  @JsonIgnore
  @TableField(exist = false)
  private String startTimeFrom;

  /**
   * 结束时间选择器
   */
  @JsonIgnore
  @TableField(exist = false)
  private String endTimeTo;

  /**
   * 考试状态：已经开始
   *
   * @return {@link Boolean}
   */
  public boolean getIsStart() {
    return this.checkIsStart();
  }

  /**
   * 考试状态：已经结束
   *
   * @return {@link Boolean}
   */
  public boolean getIsEnd() {
    return this.checkIsEnd();
  }

  /**
   * 考试状态：正在进行
   *
   * @return {@link Boolean}
   */
  public boolean getIsBetween() {
    return this.checkIsBetween();
  }

  /**
   * 是否已经开始
   *
   * @return {@link Boolean}
   */
  private boolean checkIsStart() {
    return !getType().equals(TYPE_NORMAL) || getStartTime().before(new Date());
  }

  /**
   * 是否已经结束
   *
   * @return {@link Boolean}
   */
  private boolean checkIsEnd() {
    return !getType().equals(TYPE_NORMAL) || getEndTime().before(new Date());
  }

  /**
   * 是否正在考试中
   *
   * @return {@link Boolean}
   */
  private boolean checkIsBetween() {
    return checkIsStart() && !checkIsEnd();
  }

}