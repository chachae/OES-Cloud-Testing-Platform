package com.oes.common.core.exam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课程-教师中间表实体类
 *
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
@Data
@NoArgsConstructor
@TableName("t_course_teacher")
public class CourseTeacher implements Serializable {

  private static final long serialVersionUID = -4042882453735370512L;

  /**
   * 课程编号（id）
   */
  @TableId(type = IdType.INPUT)
  private Long courseId;

  /**
   * 教师编号（id）
   */
  private Long teacherId;

  /**
   * 教师姓名
   */
  @TableField(exist = false)
  private String teacherName;


  public CourseTeacher(Long courseId, Long teacherId) {
    this.courseId = courseId;
    this.teacherId = teacherId;
  }

}