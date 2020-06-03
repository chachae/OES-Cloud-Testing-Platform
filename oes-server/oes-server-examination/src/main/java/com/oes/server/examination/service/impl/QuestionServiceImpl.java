package com.oes.server.examination.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.constant.SystemConstant;
import com.oes.common.core.entity.QueryParam;
import com.oes.common.core.util.SortUtil;
import com.oes.server.examination.entity.system.Question;
import com.oes.server.examination.mapper.QuestionMapper;
import com.oes.server.examination.service.IQuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements
    IQuestionService {

  @Override
  public IPage<Question> pageQuestion(QueryParam param, Question question) {
    Page<Question> page = new Page<>(param.getPageNum(), param.getPageSize());
    SortUtil.handlePageSort(param, page, "question_id", SystemConstant.ORDER_ASC, false);
    return baseMapper.pageQuestion(page, question);
  }
}