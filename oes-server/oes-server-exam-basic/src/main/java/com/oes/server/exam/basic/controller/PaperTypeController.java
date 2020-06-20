package com.oes.server.exam.basic.controller;

import com.oes.common.core.entity.exam.PaperType;
import com.oes.server.exam.basic.service.IPaperTypeService;
import javax.validation.Valid;
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
@RequestMapping("paper/type")
public class PaperTypeController {

  private final IPaperTypeService paperTypeService;

  @GetMapping("check")
  public Boolean check(@Valid PaperType paperType) {
    return paperTypeService.checkScore(paperType);
  }

}
