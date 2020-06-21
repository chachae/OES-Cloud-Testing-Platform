package com.oes.server.exam.online.controller;

import com.oes.common.core.entity.QueryParam;
import com.oes.common.core.entity.R;
import com.oes.common.core.entity.exam.Paper;
import com.oes.common.core.util.PageUtil;
import com.oes.server.exam.online.service.IPaperService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
