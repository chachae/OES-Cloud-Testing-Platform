package com.oes.server.exam.basic.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oes.common.core.entity.QueryParam;
import com.oes.common.core.entity.R;
import com.oes.common.core.entity.exam.Answer;
import com.oes.common.core.util.PageUtil;
import com.oes.server.exam.basic.service.IAnswerService;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

  @GetMapping
  @PreAuthorize("hasAuthority('answer:view')")
  public R<Map<String, Object>> pageAnswer(Answer answer, QueryParam param) {
    // 模糊条件班级、试卷名、学期、姓名
    IPage<Answer> result = answerService.pageAnswer(answer, param);
    return R.ok(PageUtil.toPage(result));
  }

  @PutMapping
  @PreAuthorize("hasAuthority('answer:update')")
  public void update(@Valid Answer answer) {
    this.answerService.updateAnswer(answer);
  }

  @PostMapping
  @PreAuthorize("hasAuthority('answer:add')")
  public void add(@Valid Answer answer) {
    this.answerService.createAnswer(answer);
  }

  @DeleteMapping("{answerIds}")
  @PreAuthorize("hasAuthority('answer:delete')")
  public void delete(@NotBlank(message = "{required}") @PathVariable String answerIds) {
    String[] answerIdArray = answerIds.split(StrUtil.COMMA);
    this.answerService.deleteAnswer(answerIdArray);
  }

  @DeleteMapping
  @PreAuthorize("hasAuthority('answer:delete')")
  public void deleteByAnswer(Answer answer) {
    this.answerService.deleteAnswer(answer.getStudentId(), answer.getPaperId());
  }

}
