package com.oes.server.exam.basic.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.exam.entity.PaperDept;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
public interface IPaperDeptService extends IService<PaperDept> {

  /**
   * 通过试卷编号批量删除试卷-班级指派信息
   *
   * @param paperIds 试卷编号集合
   */
  void deleteBatchByPaperIds(String[] paperIds);

  /**
   * 通过试卷编号删除试卷-班级指派信息
   *
   * @param paperId 试卷编号
   */
  void deleteByPaperId(Long paperId);

}