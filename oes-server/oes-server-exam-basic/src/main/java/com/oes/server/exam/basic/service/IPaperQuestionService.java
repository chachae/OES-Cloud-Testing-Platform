package com.oes.server.exam.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.exam.entity.PaperQuestion;
import java.util.List;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
public interface IPaperQuestionService extends IService<PaperQuestion> {

  List<PaperQuestion> selectList(Long paperId);

  void deleteBatchByPaperIds(String[] paperIds);
}