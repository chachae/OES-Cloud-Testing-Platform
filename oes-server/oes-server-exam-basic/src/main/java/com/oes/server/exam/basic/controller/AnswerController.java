package com.oes.server.exam.basic.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oes.common.core.entity.R;
import com.oes.common.core.exam.entity.Answer;
import com.oes.common.core.exam.entity.query.QueryAnswerDto;
import com.oes.common.core.util.PageUtil;
import com.oes.common.core.validator.Update;
import com.oes.server.exam.basic.service.IAnswerService;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RestController
@RequiredArgsConstructor
@RequestMapping("answer")
public class AnswerController {

  private final IAnswerService answerService;

  @GetMapping
  @PreAuthorize("hasAuthority('answer:view')")
  public R<Map<String, Object>> pageAnswer(QueryAnswerDto answer) {
    IPage<Answer> result = answerService.pageAnswer(answer);
    return R.ok(PageUtil.toPage(result));
  }

  @PutMapping
  @PreAuthorize("hasAuthority('answer:update')")
  public void update(@Validated(Update.class) Answer answer) {
    this.answerService.updateAnswer(answer);
  }

  @GetMapping("warn")
  public R<List<Map<String, Object>>> getWarningAnswer(@NotNull(message = "{required}") Long paperId, QueryAnswerDto entity) {
    entity.setPaperId(paperId);
    return R.ok(answerService.getWarnAnswerList(entity));
  }

}
