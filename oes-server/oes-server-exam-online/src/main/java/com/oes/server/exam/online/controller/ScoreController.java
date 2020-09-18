package com.oes.server.exam.online.controller;

import com.oes.common.core.entity.R;
import com.oes.common.core.exam.entity.Score;
import com.oes.common.core.exam.entity.query.QueryScoreDto;
import com.oes.common.core.exam.entity.vo.StatisticScoreVo;
import com.oes.common.core.util.PageUtil;
import com.oes.common.core.util.SecurityUtil;
import com.oes.server.exam.online.service.IScoreService;
import java.util.Map;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 个人成绩查询
 *
 * @author chachae
 * @version v1.0
 * @date 2020/6/24 17:49
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("score")
public class ScoreController {

  private final IScoreService scoreService;

  @GetMapping("check")
  public Boolean checkScore(Score score) {
    score = scoreService.getScore(SecurityUtil.getCurrentUsername(), score.getPaperId());
    return score != null && score.getStatus() != null && score.getStatus() == 0;
  }

  @GetMapping("statistic")
  public R<StatisticScoreVo> statisticScore(@NotNull(message = "{required}") Long paperId) {
    return R.ok(this.scoreService.statisticScore(paperId));
  }

  @GetMapping
  public R<Map<String, Object>> pageScore(QueryScoreDto score) {
    return R.ok(PageUtil.toPage(scoreService.pageScore(score)));
  }

  @PostMapping
  public void add(Score score) {
    score.setUsername(SecurityUtil.getCurrentUsername());
    scoreService.updateScore(score);
  }

}
