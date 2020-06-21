package com.oes.server.exam.online.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.entity.exam.PaperDept;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
public interface IPaperDeptService extends IService<PaperDept> {

  /**
   * 通过部门编号获取试卷编号数据
   *
   * @param deptId 部门编号
   * @return 试卷编号
   */
  String getPaperId(Long deptId);

}