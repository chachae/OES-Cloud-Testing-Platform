package com.oes.server.exam.basic.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oes.common.core.entity.R;
import com.oes.common.core.exam.entity.Score;
import com.oes.common.core.exam.entity.query.QueryScoreDto;
import com.oes.common.core.util.PageUtil;
import com.oes.server.exam.basic.service.IScoreService;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/19 20:37
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("score")
public class ScoreController {

  private final IScoreService scoreService;

  @GetMapping
  @PreAuthorize("hasAuthority('score:view')")
  public R<Map<String, Object>> pageAnswer(QueryScoreDto score) {
    IPage<Score> result = scoreService.pageScore(score);
    return R.ok(PageUtil.toPage(result));
  }

  @PutMapping
  @PreAuthorize("hasAuthority('score:update')")
  public void update(@Valid Score score) {
    this.scoreService.updateScore(score);
  }

  @PostMapping
  @PreAuthorize("hasAuthority('score:add')")
  public void add(@Valid @RequestBody Score score) {
    this.scoreService.createScore(score);
  }

  @DeleteMapping
  @PreAuthorize("hasAuthority('score:delete')")
  public void deleteByAnswer(Score score) {
    this.scoreService.deleteScore(score.getStudentId(), score.getPaperId());
  }

}
