package com.oes.server.exam.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.exam.entity.Score;
import com.oes.common.core.exam.entity.query.QueryScoreDto;
import com.oes.common.core.exam.entity.vo.StatisticScoreVo;
import com.oes.common.core.exam.util.ScoreUtil;
import com.oes.common.core.util.SecurityUtil;
import com.oes.server.exam.basic.mapper.ScoreMapper;
import com.oes.server.exam.basic.service.IScoreService;
import java.util.Date;
import java.util.List;
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
  public IPage<Score> pageScore(QueryScoreDto score) {
    return baseMapper.pageScore(score, new Page<>(score.getPageNum(), score.getPageSize()));
  }

  @Override
  public List<Score> getScore(QueryScoreDto score) {
    return baseMapper.getScore(score);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteScore(String username, Long paperId) {
    if (username != null || paperId != null) {
      LambdaQueryWrapper<Score> wrapper = new LambdaQueryWrapper<>();
      if (username != null) {
        wrapper.eq(Score::getUsername, username);
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
    // 当前用户
    score.setUsername(SecurityUtil.getCurrentUsername());
    baseMapper.insert(score);
  }

  @Override
  public StatisticScoreVo statisticScore(Long paperId) {
    // 设置查询条件，其中只需要有效成绩
    QueryScoreDto entity = new QueryScoreDto();
    entity.setPaperId(paperId);
    entity.setStatus(Score.STATUS_HAS_SUBMIT);
    return ScoreUtil.statisticScore(this.getScore(entity));
  }
}