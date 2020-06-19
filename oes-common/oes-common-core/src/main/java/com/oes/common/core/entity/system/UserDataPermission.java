package com.oes.common.core.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户数据权限关联表(TUserDept)表实体类
 *
 * @author chachae
 * @since 2020-05-28 12:03:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user_data_permission")
public class UserDataPermission implements Serializable {

  private static final long serialVersionUID = -6330896351089751532L;

  private Long userId;

  private Long deptId;

}