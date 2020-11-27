package com.oes.server.exam.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.exam.entity.PaperType;
import java.util.List;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
public interface IPaperTypeService extends IService<PaperType> {

  /**
   * 分数校验
   *
   * @param paperType 试卷分数数据
   * @return {@link Boolean}
   */
  Boolean checkScore(PaperType paperType);

  /**
   * 获取试卷-类型数据集合
   *
   * @param paperId 试卷编号
   * @return {@link List<PaperType>} 试卷类型集合
   */
  List<PaperType> getList(Long paperId);

  /**
   * 获取试卷-题目类型数据
   *
   * @param paperId 试卷编号
   * @param typeId  类型编号
   * @return {@link PaperType} 试卷-题目类型数据
   */
  PaperType getPaperType(Long paperId, Long typeId);

  /**
   * 通过试卷编号删除试卷试题类型分值数据
   *
   * @param paperIds 试卷编号
   */
  void deleteBatchByPaperIds(String[] paperIds);

  /**
   * 批量插入
   *
   * @param paperQuestions 实体
   */
  void insertBatch(List<PaperType> paperQuestions);
}