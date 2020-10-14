package com.oes.server.exam.online.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.exam.entity.PaperQuestion;
import java.util.Map;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
public interface IPaperQuestionService extends IService<PaperQuestion> {

  /**
   * 通过试卷编号获取试卷题目信息
   *
   * @param paperId 试卷编号
   * @return {@link Map<Long,PaperQuestion>} Map<试题编号,题目答案信息>
   */
  Map<Long, PaperQuestion> getMapByPaperId(Long paperId);

}