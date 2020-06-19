package com.oes.common.core.entity.exam;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 试卷-部门中间表实体类
 *
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_paper_dept")
public class PaperDept implements Serializable {

  private static final long serialVersionUID = 5824101237768741729L;

  /**
   * 试卷编号（id）
   */
  @TableId(type = IdType.INPUT)
  private Long paperId;
  /**
   * 部门编号（id）
   */
  @TableId(type = IdType.INPUT)
  private Long deptId;

}