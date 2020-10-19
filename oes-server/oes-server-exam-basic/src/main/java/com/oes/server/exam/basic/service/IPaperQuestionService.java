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
  Map<Long, PaperQuestion> selectMapByPaperId(Long paperId);

  /**
   * 通过试卷编号获取试卷题目集合
   *
   * @param paperId 试卷编号
   * @return 试卷题目集合
   */
  List<PaperQuestion> getListByPaperId(Long paperId);

  void deleteBatchByPaperIds(String[] paperIds);
}