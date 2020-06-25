package com.oes.server.exam.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.entity.QueryParam;
import com.oes.common.core.exam.entity.Score;
import com.oes.server.exam.basic.mapper.ScoreMapper;
import com.oes.server.exam.basic.service.IScoreService;
import java.util.Date;
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
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score> implements IScoreService {

  @Override
  public IPage<Score> pageScore(Score score, QueryParam param) {
    return baseMapper.pageScore(score, new Page<>(param.getPageNum(), param.getPageSize()));
  }

  @Override
  public Score getScore(Long userId, Long paperId) {
    LambdaQueryWrapper<Score> wrapper = new LambdaQueryWrapper<>();
    wrapper
        .eq(Score::getStudentId, userId)
        .eq(Score::getPaperId, paperId);
    return baseMapper.selectOne(wrapper);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteScore(Long userId, Long paperId) {
    if (userId != null || paperId != null) {
      LambdaQueryWrapper<Score> wrapper = new LambdaQueryWrapper<>();
      if (userId != null) {
        wrapper.eq(Score::getStudentId, userId);
      }
      if (paperId != null) {
        wrapper.eq(Score::getPaperId, paperId);
      }
      baseMapper.delete(wrapper);
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateScore(Score score) {
    score.setUpdateTime(new Date());
    baseMapper.updateById(score);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void createScore(Score score) {
    // 默认分数
    score.setStudentScore(Score.DEFAULT_SCORE);
    score.setCreateTime(new Date());
    baseMapper.insert(score);
  }
}