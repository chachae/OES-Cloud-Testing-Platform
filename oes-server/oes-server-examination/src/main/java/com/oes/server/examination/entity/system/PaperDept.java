package com.oes.server.examination.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 试卷-部门中间表实体类
 *
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
@Data
@TableName("t_paper_dept")
public class PaperDept implements Serializable {

  private static final long serialVersionUID = 5824101237768741729L;

  /**
   * 试卷编号（id）
   */
  private Long paperId;
  /**
   * 部门编号（id）
   */
  private Long deptId;

}