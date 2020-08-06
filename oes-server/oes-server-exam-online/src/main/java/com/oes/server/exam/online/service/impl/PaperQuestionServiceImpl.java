package com.oes.server.exam.online.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.constant.DataSourceConstant;
import com.oes.common.core.constant.SystemConstant;
import com.oes.common.core.exam.entity.PaperQuestion;
import com.oes.server.exam.online.cache.PaperQuestionCacheService;
import com.oes.server.exam.online.mapper.PaperQuestionMapper;
import com.oes.server.exam.online.service.IPaperQuestionService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PaperQuestionServiceImpl extends
    ServiceImpl<PaperQuestionMapper, PaperQuestion> implements
    IPaperQuestionService {

  private final PaperQuestionCacheService paperQuestionCacheService;

  @Override
  @DS(DataSourceConstant.SLAVE)
  public Map<String, PaperQuestion> getMapByPaperId(Long paperId) {
    Map<String, PaperQuestion> res = paperQuestionCacheService
        .get(SystemConstant.PAPER_QUESTION_PREFIX + paperId);
    if (res != null) {
      return res;
    }
    List<PaperQuestion> paperQuestions = baseMapper.selectByPaperId(paperId);
    Map<String, PaperQuestion> pqMap = new HashMap<>(paperQuestions.size());
    paperQuestions.forEach(pq -> pqMap.put(String.valueOf(pq.getQuestionId()), pq));
    paperQuestionCacheService
        .save(SystemConstant.PAPER_QUESTION_PREFIX + paperId, pqMap);
    return pqMap;
  }
}