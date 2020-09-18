package com.oes.common.core.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.oes.common.core.validator.annotation.IDCard;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 身份核验表实体类
 *
 * @author chachae
 * @since 2020-06-30 22:33:11
 */
@Data
@TableName("t_idcard_verify")
public class IdCardVerify implements Serializable {

  private static final long serialVersionUID = 407284558450301629L;

  /**
   * 主键
   */
  @TableId(type = IdType.AUTO)
  private Long verifyId;

  /**
   * 用户编号
   */
  private Long userId;

  /**
   * 身份证姓名
   */
  private String name;

  /**
   * 身份证地址
   */
  private String address;

  /**
   * 身份证号
   */
  @IDCard(message = "不是有效的身份证")
  private String num;

  /**
   * 身份证性别
   */
  private String sex;

  /**
   * 身份证生日
   */
  private String birth;

  /**
   * 身份证头像base64编码
   */
  private String photo;

  /**
   * 过期时间
   */
  private String endDate;

  /**
   * 签发时间
   */
  private String startDate;

  /**
   * 签发机关
   */
  private String issue;

  /**
   * 身份核验状态（1：通过，0：失败）
   */
  private Boolean success;

  /**
   * 是否为复印件等
   */
  @TableField("`fake`")
  private Boolean isFake;

  private Date createTime;

  private Date updateTime;

}