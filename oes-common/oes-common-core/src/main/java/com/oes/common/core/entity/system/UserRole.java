package com.oes.common.core.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户角色关联表实体类
 *
 * @author chachae
 * @since 2020-04-29 21:25:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user_role")
public class UserRole {

  /**
   * 用户ID
   */
  @TableId(type = IdType.INPUT)
  private Long userId;
  /**
   * 角色ID
   */
  private Long roleId;

  public UserRole(Long userId) {
    this.userId = userId;
  }


}