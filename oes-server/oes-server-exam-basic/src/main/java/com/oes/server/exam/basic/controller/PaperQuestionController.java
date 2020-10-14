package com.oes.server.exam.basic.controller;

import com.oes.common.core.entity.R;
import com.oes.common.core.exam.entity.PaperQuestion;
import com.oes.server.exam.basic.service.IPaperQuestionService;
import java.util.Map;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/20 12:29
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("paper-question")
public class PaperQuestionController {

  private final IPaperQuestionService paperQuestionService;

  @GetMapping("list")
  public R<Map<Long, PaperQuestion>> getMap(@NotNull(message = "{required}") Long paperId) {
    return R.ok(paperQuestionService.selectMapByPaperId(paperId));
  }

}
