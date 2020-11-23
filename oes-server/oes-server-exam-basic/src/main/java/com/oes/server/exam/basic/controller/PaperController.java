package com.oes.server.exam.basic.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oes.common.core.entity.R;
import com.oes.common.core.exam.entity.Paper;
import com.oes.common.core.exam.entity.PaperType;
import com.oes.common.core.exam.entity.query.QueryPaperDto;
import com.oes.common.core.util.PageUtil;
import com.oes.common.core.util.SecurityUtil;
import com.oes.server.exam.basic.service.IPaperService;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
  public R<Map<String, Object>> pageCourse(QueryPaperDto paper) {
    IPage<Paper> result = paperService.pagePaper(paper);
    return R.ok(PageUtil.toPage(result));
  }

  @GetMapping("{paperId}")
  public R<Paper> getOne(@PathVariable("paperId") @NotNull(message = "{required}") Long paperId) {
    Paper paper = paperService.getPaper(paperId, SecurityUtil.getCurrentUsername());
    return R.ok(paper);
  }

  @PutMapping
  @PreAuthorize("hasAuthority('paper:update')")
  public void updateStatus(@Valid Paper paper) {
    paperService.updatePaper(paper);
  }

  @PostMapping("random")
  @PreAuthorize("hasAuthority('paper:add')")
  public void randomCreatePaper(@Valid Paper paper, @Valid PaperType paperTypes) {
    paperService.randomCreatePaper(paper, paperTypes);
  }

  @DeleteMapping("{paperIds}")
  @PreAuthorize("hasAuthority('paper:delete')")
  public void delete(@NotBlank(message = "{required}") @PathVariable String paperIds) {
    String[] paperIdArray = paperIds.split(StrUtil.COMMA);
    this.paperService.deletePaper(paperIdArray);
  }

}
