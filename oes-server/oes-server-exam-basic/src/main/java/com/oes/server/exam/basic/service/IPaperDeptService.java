package com.oes.server.exam.basic.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.exam.entity.PaperDept;
import java.util.List;

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

  /**
   * 通过试卷编号获取考试部门（班级）
   *
   * @param paperId 试卷编号
   */
  List<Long> getDeptIdListByPaperId(Long paperId);

  /**
   * 统计指派班级数量
   *
   * @param paperIds 试卷编号
   * @return 数量
   */
  Integer countByPaperId(String[] paperIds);

  /**
   * 批量插入
   *
   * @param paperDept 实体
   */
  void insertBatch(List<PaperDept> paperDept);
}