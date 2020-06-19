package com.oes.server.examination.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.entity.QueryParam;
import com.oes.common.core.entity.examination.Answer;
import com.oes.server.examination.mapper.AnswerMapper;
import com.oes.server.examination.service.IAnswerService;
import java.util.Date;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chachae
 * @since 2020-06-03 16:43:14
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements IAnswerService {

  @Override
  public IPage<Answer> pageAnswer(Answer answer, QueryParam param) {
    return baseMapper.pageAnswer(answer, new Page<>(param.getPageNum(), param.getPageSize()));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteAnswer(Long studentId, Long paperId) {
    if (studentId != null || paperId != null) {
      LambdaQueryWrapper<Answer> wrapper = new LambdaQueryWrapper<>();
      if (studentId != null) {
        wrapper.eq(Answer::getStudentId, studentId);
      }
      if (paperId != null) {
        wrapper.eq(Answer::getPaperId, paperId);
      }
      baseMapper.delete(wrapper);
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateAnswer(Answer answer) {
    answer.setUpdateTime(new Date());
    baseMapper.updateById(answer);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void createAnswer(Answer answer) {
    answer.setCreateTime(new Date());
    baseMapper.insert(answer);
  }
}