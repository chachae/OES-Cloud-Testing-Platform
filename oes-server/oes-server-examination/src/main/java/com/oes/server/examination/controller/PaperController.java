package com.oes.server.examination.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oes.common.core.entity.QueryParam;
import com.oes.common.core.entity.R;
import com.oes.common.core.util.PageUtil;
import com.oes.server.examination.entity.system.Paper;
import com.oes.server.examination.service.IPaperService;
import java.util.Map;
import javax.validation.Valid;
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
 * @date 2020/6/7 22:40
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("paper")
public class PaperController {

  private final IPaperService paperService;

  @GetMapping
  @PreAuthorize("hasAuthority('paper:view')")
  public R<Map<String, Object>> pageCourse(Paper paper, QueryParam param) {
    IPage<Paper> result = paperService.pagePaper(paper, param);
    return R.ok(PageUtil.toPage(result));
  }

  @PutMapping
  @PreAuthorize("hasAuthority('paper:update')")
  public void updateStatus(@Valid Paper paper) {
    paperService.updatePaper(paper);
  }


}