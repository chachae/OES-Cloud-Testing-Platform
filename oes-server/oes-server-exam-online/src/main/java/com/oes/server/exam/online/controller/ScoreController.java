package com.oes.server.exam.online.controller;

import com.oes.common.core.entity.R;
import com.oes.common.core.exam.entity.Score;
import com.oes.common.core.exam.entity.query.QueryScoreDto;
import com.oes.common.core.util.PageUtil;
import com.oes.common.core.util.SecurityUtil;
import com.oes.server.exam.online.service.IScoreService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
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
    Integer status = scoreService.getScore(null, score.getPaperId()).getStatus();
    return status != null && status == 0;
  }

  @GetMapping
  public R<Map<String, Object>> pageScore(QueryScoreDto score) {
    score.setStudentId(SecurityUtil.getCurrentUser().getUserId());
    return R.ok(PageUtil.toPage(scoreService.getScore(score)));
  }

  @PostMapping
  public void add(Score score) {
    scoreService.updateScore(score);
  }

}
