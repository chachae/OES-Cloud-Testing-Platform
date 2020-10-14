package com.oes.server.exam.basic.controller;

import com.oes.common.core.entity.R;
import com.oes.server.exam.basic.service.IPaperDeptService;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/20 12:29
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("paper-dept")
public class PaperDeptController {

  private final IPaperDeptService paperDeptService;

  @GetMapping("options")
  public R<List<Long>> options(@NotNull(message = "{required}") Long paperId) {
    return R.ok(paperDeptService.getDeptIdListByPaperId(paperId));
  }

}
