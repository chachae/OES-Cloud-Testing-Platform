package com.oes.server.exam.online.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.constant.SystemConstant;
import com.oes.common.core.exam.entity.Answer;
import com.oes.common.core.exam.entity.Score;
import com.oes.common.core.exam.entity.query.QueryScoreDto;
import com.oes.common.core.exam.util.ScoreUtil;
import com.oes.common.core.util.SecurityUtil;
import com.oes.common.core.util.SortUtil;
import com.oes.server.exam.online.mapper.ScoreMapper;
import com.oes.server.exam.online.service.IAnswerService;
import com.oes.server.exam.online.service.IScoreService;
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

  private final IAnswerService answerService;

  @Override
  public IPage<Score> getScore(QueryScoreDto score) {
    if (score.getStudentId() == null) {
      score.setStudentId(SecurityUtil.getCurrentUser().getUserId());
    }
    Page<Score> page = new Page<>(score.getPageNum(), score.getPageSize());
    // 根据学期降序排序
    SortUtil.handlePageSort(score, page, "tt.term_id", SystemConstant.ORDER_DESC, false);
    return baseMapper.pageScore(score, page);
  }

  @Override
  public Score getScore(Long userId, Long paperId) {
    if (userId == null) {
      userId = SecurityUtil.getCurrentUser().getUserId();
    }
    LambdaQueryWrapper<Score> wrapper = new LambdaQueryWrapper<>();
    wrapper
        .eq(Score::getStudentId, userId)
        .eq(Score::getPaperId, paperId);
    return baseMapper.selectOne(wrapper);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void createScore(Score score) {
    // 默认分数
    score.setStudentScore(Score.DEFAULT_SCORE);
    score.setStatus(Score.STATUS_NOT_SUBMIT);
    score.setCreateTime(new Date());
    baseMapper.insert(score);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateScore(Score score) {
    // 计算答卷耗时
    Score res = getScore(score.getStudentId(), score.getPaperId());
    score.setUpdateTime(new Date());
    score.setTimes(ScoreUtil.calTimes(res.getCreateTime()));
    // 计算成绩
    List<Answer> answers = answerService.getAnswer(score.getStudentId(), score.getPaperId());
    score.setStudentScore(ScoreUtil.calScore(answers));
    score.setStatus(Score.STATUS_HAS_SUBMIT);
    baseMapper
        .update(score, new LambdaQueryWrapper<Score>().eq(Score::getStudentId, score.getStudentId())
            .eq(Score::getPaperId, score.getPaperId()));
  }
}