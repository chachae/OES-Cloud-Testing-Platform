package com.oes.server.exam.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.exam.entity.PaperQuestion;
import java.util.List;
import java.util.Map;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
public interface IPaperQuestionService extends IService<PaperQuestion> {

  /**
   * 通过试卷编号获取试卷题目Map
   *
   * @param paperId 试卷编号
   * @return Map<String, PaperQuestion> key=试题id，value=试题信息
   */
  Map<Long, PaperQuestion> getMapByPaperId(Long paperId);

  /**
   * 通过试卷编号获取题目编号集合
   *
   * @param paperId 试卷编号
   * @return 题目编号集合
   */
  List<Long> getQuestionIdsByPaperId(Long paperId);

  /**
   * 批量插入
   *
   * @param paperQuestions 实体
   */
  void insertBatch(List<PaperQuestion> paperQuestions);

  /**
   * 通过试卷编号获取试卷题目集合
   *
   * @param paperId 试卷编号
   * @return 试卷题目集合
   */
  List<PaperQuestion> getListByPaperId(Long paperId);

  /**
   * 通过试卷编号获取试卷题目集合（针对考试列出的信息）
   *
   * @param paperId 试卷编号
   * @return 试卷题目集合
   */
  List<PaperQuestion> getExamInfoListByPaperId(Long paperId);

  /**
   * 根据题目编号计算试卷关联数量
   *
   * @param questionIds 题目编号
   * @return 数量
   */
  Integer countByQuestionIds(String[] questionIds);

  /**
   * 根据试卷编号批量删除题目编号-试卷编号关联数据
   *
   * @param paperIds 试卷编号数组
   */
  void deleteBatchByPaperIds(String[] paperIds);
}