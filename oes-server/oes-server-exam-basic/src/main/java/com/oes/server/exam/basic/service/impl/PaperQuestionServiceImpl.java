package com.oes.server.exam.basic.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.constant.DataSourceConstant;
import com.oes.common.core.exam.entity.PaperQuestion;
import com.oes.server.exam.basic.mapper.PaperQuestionMapper;
import com.oes.server.exam.basic.service.IPaperQuestionService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PaperQuestionServiceImpl extends
    ServiceImpl<PaperQuestionMapper, PaperQuestion> implements
    IPaperQuestionService {

  @Override
  @DS(DataSourceConstant.SLAVE)
  public List<PaperQuestion> selectList(Long paperId) {
    return baseMapper.selectListByPaperId(paperId);
  }
}