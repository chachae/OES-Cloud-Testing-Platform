package com.oes.server.exam.online.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.exam.entity.PaperDept;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
public interface IPaperDeptService extends IService<PaperDept> {

  /**
   * 获取试卷部门编号信息
   *
   * @param paperId 试卷编号
   * @return 部门编号
   */
  String getDeptIds(Long paperId);
}