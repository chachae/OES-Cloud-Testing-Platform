package com.oes.server.exam.basic.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oes.common.core.entity.R;
import com.oes.common.core.exam.entity.Question;
import com.oes.common.core.exam.entity.query.QueryQuestionDto;
import com.oes.common.core.util.PageUtil;
import com.oes.server.exam.basic.service.IQuestionService;
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
 * @date 2020/6/5 23:00
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("question")
public class QuestionController {

  private final IQuestionService questionService;

  @GetMapping
  @PreAuthorize("hasAuthority('question:view')")
  public R<Map<String, Object>> pageType(QueryQuestionDto question) {
    IPage<Question> result = this.questionService.pageQuestion(question);
    return R.ok(PageUtil.toPage(result));
  }

  @PostMapping
  @PreAuthorize("hasAuthority('question:add')")
  public void add(@Valid Question question) {
    questionService.createQuestion(question);
  }

  @DeleteMapping("{questionIds}")
  @PreAuthorize("hasAuthority('question:delete')")
  public void remove(@NotBlank(message = "{required}") @PathVariable String questionIds) {
    String[] questionIdArray = questionIds.split(StrUtil.COMMA);
    questionService.deleteQuestion(questionIdArray);
  }

  @PutMapping
  @PreAuthorize("hasAuthority('question:update')")
  public void update(@Valid Question question) {
    questionService.updateQuestion(question);
  }

}
