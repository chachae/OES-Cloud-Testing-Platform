package com.oes.ai.entity.ocr;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import lombok.Data;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/29 13:04
 */

@Data
@JsonInclude(Include.NON_NULL)
public class IdCardInfo implements Serializable {

  private static final long serialVersionUID = -6235009938110056721L;

  /**
   * 姓名
   */
  private String name;

  /**
   * 户口地址
   */
  private String address;

  /**
   * 民族
   */
  private String nationality;

  /**
   * 身份证
   */
  private String num;

  /**
   * 性别
   */
  private String sex;

  /**
   * 出生日期
   */
  private String birth;

  /**
   * 有效期起始时间
   */
  private String startDate;

  /**
   * 有效期结束时间
   */
  private String endDate;

  /**
   * 签发机关
   */
  private String issue;

  /**
   * 是否为复印件
   */
  private Boolean isFake;

  /**
   * 识别结果
   */
  private Boolean success;

  /**
   * 失败信息
   */
  private String message;

  /**
   * 身份证头像裁剪
   */
  private String photo;

}
