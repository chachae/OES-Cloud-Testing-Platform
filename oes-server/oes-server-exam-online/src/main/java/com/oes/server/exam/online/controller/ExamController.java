package com.oes.server.exam.online.controller;

import com.oes.common.core.entity.QueryParam;
import com.oes.common.core.entity.R;
import com.oes.common.core.exam.entity.Paper;
import com.oes.common.core.exam.entity.Score;
import com.oes.common.core.util.PageUtil;
import com.oes.server.exam.online.service.IPaperService;
import com.oes.server.exam.online.service.IScoreService;
import java.util.List;
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
   * <pre>
   *   How to curl cur api
   *   for example:exam/one/11?studentId=1
   * </pre>
   */
  @GetMapping("one/{paperId}")
  public R<List<Map<String, Object>>> getExam(@PathVariable Long paperId,
      @NotNull(message = "{required}") Long studentId) {
    Score res = scoreService.getScore(studentId, paperId);
    if (res == null) {
      Score score = new Score();
      score.setStudentId(studentId);
      score.setPaperId(paperId);
      scoreService.createScore(score);
    }
    return R.ok(paperService.getByPaperIdAndStudentId(paperId, studentId));
  }

  @GetMapping("normal")
  public R<Map<String, Object>> pageNormalPaper(QueryParam param, Paper paper) {
    Map<String, Object> map = PageUtil.toPage(paperService.getNormalPaper(param, paper));
    return R.ok(map);
  }

  @GetMapping("imitate")
  public R<Map<String, Object>> pageImitatePaper(QueryParam param, Paper paper) {
    Map<String, Object> map = PageUtil.toPage(paperService.getImitatePaper(param, paper));
    return R.ok(map);
  }
}
