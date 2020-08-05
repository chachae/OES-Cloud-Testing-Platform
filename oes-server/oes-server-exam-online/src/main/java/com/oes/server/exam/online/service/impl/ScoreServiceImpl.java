package com.oes.server.exam.online.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.constant.SystemConstant;
import com.oes.common.core.entity.R;
import com.oes.common.core.exam.entity.Answer;
import com.oes.common.core.exam.entity.Score;
import com.oes.common.core.exam.entity.query.QueryScoreDto;
import com.oes.common.core.exam.entity.vo.StatisticScoreVo;
import com.oes.common.core.exam.util.ScoreUtil;
import com.oes.common.core.util.SecurityUtil;
import com.oes.common.core.util.SortUtil;
import com.oes.server.exam.online.client.SystemUserClient;
import com.oes.server.exam.online.mapper.ScoreMapper;
import com.oes.server.exam.online.service.IAnswerService;
import com.oes.server.exam.online.service.IPaperDeptService;
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

  private final SystemUserClient systemUserClient;
  private final IAnswerService answerService;
  private final IPaperDeptService paperDeptService;

  @Override
  public IPage<Score> pageScore(QueryScoreDto score) {
    if (score.getUsername() == null) {
      score.setUsername(SecurityUtil.getCurrentUsername());
    }
    score.setStatus(Score.STATUS_HAS_SUBMIT);
    Page<Score> page = new Page<>(score.getPageNum(), score.getPageSize());
    // 根据学期降序排序
    SortUtil.handlePageSort(score, page, "tt.term_id", SystemConstant.ORDER_DESC, false);
    return baseMapper.pageScore(score, page);
  }

  @Override
  public List<Score> getScore(QueryScoreDto score) {
    return baseMapper.getScore(score);
  }

  @Override
  public Score getScore(String username, Long paperId) {
    if (username == null) {
      username = SecurityUtil.getCurrentUsername();
    }
    LambdaQueryWrapper<Score> wrapper = new LambdaQueryWrapper<>();
    wrapper
        .eq(Score::getUsername, username)
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
    Score res = getScore(score.getUsername(), score.getPaperId());
    score.setUpdateTime(new Date());
    score.setTimes(ScoreUtil.calTimes(res.getCreateTime()));
    // 计算成绩
    List<Answer> answers = answerService.getAnswer(score.getUsername(), score.getPaperId());
    score.setStudentScore(ScoreUtil.calScore(answers));
    score.setStatus(Score.STATUS_HAS_SUBMIT);
    baseMapper
        .update(score, new LambdaQueryWrapper<Score>()
            .eq(Score::getUsername, score.getUsername())
            .eq(Score::getPaperId, score.getPaperId()));
  }

  @Override
  public StatisticScoreVo statisticScore(Long paperId) {
    // 设置查询条件，其中只需要有效成绩
    QueryScoreDto entity = new QueryScoreDto();
    entity.setPaperId(paperId);
    entity.setStatus(Score.STATUS_HAS_SUBMIT);
    String deptIds = paperDeptService.getDeptIds(paperId);
    // 获取考生人数
    R<Integer> res = systemUserClient.countByDeptIds(deptIds);
    return ScoreUtil.statisticScore(this.getScore(entity),
        getScore(SecurityUtil.getCurrentUsername(), paperId), res.getData());
  }
}