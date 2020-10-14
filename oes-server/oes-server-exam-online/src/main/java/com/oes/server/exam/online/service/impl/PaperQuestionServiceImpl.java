package com.oes.server.exam.online.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.entity.R;
import com.oes.common.core.exam.entity.PaperQuestion;
import com.oes.server.exam.online.client.PaperQuestionClient;
import com.oes.server.exam.online.mapper.PaperQuestionMapper;
import com.oes.server.exam.online.service.IPaperQuestionService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
@Service
@RequiredArgsConstructor
public class PaperQuestionServiceImpl extends ServiceImpl<PaperQuestionMapper, PaperQuestion> implements IPaperQuestionService {

  private final PaperQuestionClient paperQuestionClient;

  @Override
  public Map<Long, PaperQuestion> getMapByPaperId(Long paperId) {
    R<Map<Long, PaperQuestion>> result = paperQuestionClient.getMap(paperId);
    return result.getData();
  }
}