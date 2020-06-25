package com.oes.server.exam.online.controller;

import com.oes.common.core.exam.entity.Score;
import com.oes.server.exam.online.service.IScoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
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

  @PostMapping
  public void add(Score score) {
    scoreService.updateScore(score);
  }

}
