package com.oes.server.exam.online.controller;

import com.oes.common.core.entity.R;
import com.oes.common.core.exam.entity.Paper;
import com.oes.common.core.exam.entity.Score;
import com.oes.common.core.exam.entity.query.QueryPaperDto;
import com.oes.common.core.util.PageUtil;
import com.oes.common.core.util.SecurityUtil;
import com.oes.server.exam.online.service.IPaperService;
import com.oes.server.exam.online.service.IScoreService;
import java.util.Map;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 在线考试控制层
 *
 * @author chachae
 * @version v1.0
 * @date 2020/6/21 18:23
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("exam")
public class ExamController {

  private final IPaperService paperService;
  private final IScoreService scoreService;

  /**
   * 获取当前考试的试卷信息
   */
  @GetMapping("{paperId}")
  public R<Paper> getExam(@PathVariable @NotNull(message = "{required}") Long paperId) {
    Paper paper = paperService.getOnePaper(paperId);
    if (paper != null) {
      String username = SecurityUtil.getCurrentUsername();
      Score res = scoreService.getScore(username, paperId);
      if (res == null) {
        Score score = new Score();
        score.setUsername(username);
        score.setPaperId(paperId);
        scoreService.createScore(score);
      }
    }
    return R.ok(paper);
  }

  /**
   * 获取正式考试信息
   *
   * @return {@link R<Map>} 返回结果
   */
  @GetMapping("normal")
  public R<Map<String, Object>> pageNormalPaper(QueryPaperDto entity) {
    Map<String, Object> map = PageUtil.toPage(paperService.getNormalPaper(entity));
    return R.ok(map);
  }

  /**
   * 获取模拟考试信息
   *
   * @return {@link R<Map>} 返回结果
   */
  @GetMapping("imitate")
  public R<Map<String, Object>> pageImitatePaper(QueryPaperDto entity) {
    Map<String, Object> map = PageUtil.toPage(paperService.getImitatePaper(entity));
    return R.ok(map);
  }
}
