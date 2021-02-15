package com.oes.server.exam.online.controller;

import com.oes.common.core.entity.R;
import com.oes.common.core.exam.entity.Answer;
import com.oes.server.exam.online.service.IAnswerService;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/19 17:14
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("answer")
public class AnswerController {

  private final IAnswerService answerService;

  /**
   * 获取错题集
   *
   * @return {@link R<List>} 错题集
   */
  @GetMapping("warn")
  public R<List<Map<String, Object>>> getWarningAnswer(@NotNull(message = "{required}") Long paperId) {
    this.answerService.statisticAnswers(1L);
    return R.ok(this.answerService.getWarnAnswerByPaperId(paperId));
  }

  /**
   * 更新试题
   *
   * @param answer 试题内容
   * @return 试题id
   */
  @PutMapping
  public R<Object> updateAnswer(Answer answer) {
    this.answerService.updateAnswer(answer);
    return R.ok();
  }

  // todo 试题答题统计，有bug，但未被使用到，暂无影响
  @Deprecated
  @GetMapping("statistic")
  public R<List<Map<String, Object>>> answerStatistic(@NotNull(message = "{required}") Long paperId) {
    return R.ok(this.answerService.statisticAnswers(paperId));
  }
}
