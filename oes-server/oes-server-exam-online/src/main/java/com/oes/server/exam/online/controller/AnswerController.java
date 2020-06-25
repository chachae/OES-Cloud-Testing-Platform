package com.oes.server.exam.online.controller;

import com.oes.common.core.entity.R;
import com.oes.common.core.exam.entity.Answer;
import com.oes.server.exam.online.service.IAnswerService;
import java.util.List;
import javax.validation.Valid;
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

  @GetMapping("one")
  public R<List<Answer>> pageAnswer(Answer answer) {
    return R.ok(answerService.getAnswer(answer.getStudentId(), answer.getPaperId()));
  }

  @PutMapping
  public R<Long> updateAnswer(@Valid Answer answer) {
    return R.ok(answerService.updateAnswer(answer));
  }

}
