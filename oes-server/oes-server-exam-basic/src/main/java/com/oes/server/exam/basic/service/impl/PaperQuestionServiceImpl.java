package com.oes.server.exam.basic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.exam.entity.PaperQuestion;
import com.oes.server.exam.basic.mapper.PaperQuestionMapper;
import com.oes.server.exam.basic.service.IPaperQuestionService;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PaperQuestionServiceImpl extends ServiceImpl<PaperQuestionMapper, PaperQuestion> implements IPaperQuestionService {

  // todo 缓存缓存
  @Override
  public Map<Long, PaperQuestion> selectMapByPaperId(Long paperId) {
    List<PaperQuestion> list = getListByPaperId(paperId);
    Map<Long, PaperQuestion> ansMap = new HashMap<>(list.size());
    list.forEach(question -> ansMap.put(question.getQuestionId(), question));
    return ansMap;
  }

  @Override
  public List<PaperQuestion> getListByPaperId(Long paperId) {
    return baseMapper.selectListByPaperId(paperId);
  }

  @Override
  public void deleteBatchByPaperIds(String[] paperIds) {
    if (paperIds.length == 1) {
      baseMapper.deleteById(paperIds[0]);
    } else {
      baseMapper.deleteBatchIds(Arrays.asList(paperIds));
    }
  }
}