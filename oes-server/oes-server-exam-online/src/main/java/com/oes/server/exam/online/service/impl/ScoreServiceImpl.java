package com.oes.server.exam.online.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.constant.SystemConstant;
import com.oes.common.core.entity.R;
import com.oes.common.core.exam.entity.Score;
import com.oes.common.core.exam.entity.query.QueryScoreDto;
import com.oes.common.core.exam.entity.vo.StatisticScoreVo;
import com.oes.common.core.exam.util.ScoreUtil;
import com.oes.common.core.util.SecurityUtil;
import com.oes.common.core.util.SortUtil;
import com.oes.server.exam.online.client.PaperDeptClient;
import com.oes.server.exam.online.client.SystemUserClient;
import com.oes.server.exam.online.mapper.ScoreMapper;
import com.oes.server.exam.online.producer.ScoreMessageSender;
import com.oes.server.exam.online.service.IScoreService;
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

  private final PaperDeptClient paperDeptClient;
  private final SystemUserClient systemUserClient;
  private final ScoreMessageSender scoreMessageSender;

  @Override
  public IPage<Score> pageScore(QueryScoreDto score) {
    score.setUsername(SecurityUtil.getCurrentUsername());
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
    LambdaQueryWrapper<Score> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(Score::getUsername, username).eq(Score::getPaperId, paperId);
    return baseMapper.selectOne(wrapper);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void createScore(Score score) {
    // 默认分数
    score.setStudentScore(Score.DEFAULT_SCORE);
    score.setStatus(Score.STATUS_NOT_SUBMIT);
    baseMapper.insert(score);
  }

  @Override
  public void updateScore(Score score) {
    scoreMessageSender.sendMarkScoreMessage(score);
  }

  @Override
  public StatisticScoreVo statisticScore(Long paperId) {
    // 设置查询条件，其中只需要有效成绩
    QueryScoreDto entity = new QueryScoreDto();
    entity.setPaperId(paperId);
    entity.setStatus(Score.STATUS_HAS_SUBMIT);
    R<List<Long>> deptsAns = paperDeptClient.getPaperDeptListByPaperId(paperId);
    // 获取考生人数
    R<Integer> res = systemUserClient.countByDeptIds(StrUtil.join(",", deptsAns.getData()));
    return ScoreUtil.statisticScore(this.getScore(entity), getScore(SecurityUtil.getCurrentUsername(), paperId), res.getData());
  }
}