/*
 *
 * Copyright 2017-2021 chachae@foxmail.com(chenyuexin)
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, see <http://www.gnu.org/licenses/>.
 *
 */
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
 * 试题中转表
 * @author chachae
 * @date 2021/2/15
 * @since TODO
 */
@Data
@TableName("t_question_transfer")
public class QuestionTransfer implements Serializable {

  private static final long serialVersionUID = -5370932290202511260L;
  /**
   * 题目主键（id）
   */
  @TableId(type = IdType.AUTO)
  private Long questionId;

  /**
   * 导入批次ID
   */
  private String importId;

  /**
   * 题干
   */
  private String questionName;

  /**
   * 题目图片
   */
  private String questionImage;

  /**
   * 选项
   */
  private String options;

  /**
   * 正确答案
   */
  private String rightKey;

  /**
   * 试题解析
   */
  private String analysis;

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

  /**
   * 创建人用户编号
   */
  private Long creatorId;

  /**
   * 出题人姓名
   */
  private String creatorName;

  /**
   * 题目类型
   */
  private Long typeId;

  /**
   * 所属课程
   */
  private Long courseId;

  /**
   * 试题难度
   */
  private Integer difficult;

  /**
   * 使用量
   */
  private Integer usedCount;

  /**
   * 填空数量
   */
  private Integer fillCount;

  /**
   * 题目类型名称
   */
  @TableField(exist = false)
  private String typeName;

  /**
   * 课程名称
   */
  @TableField(exist = false)
  private String courseName;

}
