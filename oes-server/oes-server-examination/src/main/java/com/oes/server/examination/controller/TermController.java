package com.oes.server.examination.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oes.common.core.entity.QueryParam;
import com.oes.common.core.entity.R;
import com.oes.common.core.util.PageUtil;
import com.oes.server.examination.entity.system.Term;
import com.oes.server.examination.service.ITermService;
import java.util.List;
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
 * @date 2020/6/11 16:20
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("term")
public class TermController {

  private final ITermService termService;

  @GetMapping
  @PreAuthorize("hasAuthority('term:view')")
  public R<Map<String, Object>> pageCourse(Term term, QueryParam param) {
    IPage<Term> result = termService.pageTerm(term, param);
    return R.ok(PageUtil.toPage(result));
  }

  @GetMapping("options")
  public R<List<Term>> options() {
    List<Term> result = this.termService.list();
    return R.ok(result);
  }

  @PutMapping
  @PreAuthorize("hasAuthority('term:update')")
  public void update(@Valid Term term) {
    this.termService.updateTerm(term);
  }

  @PostMapping
  @PreAuthorize("hasAuthority('term:add')")
  public void add(@Valid Term term) {
    this.termService.createTerm(term);
  }

  @DeleteMapping("{termIds}")
  @PreAuthorize("hasAuthority('term:delete')")
  public void delete(@NotBlank(message = "{required}") @PathVariable String termIds) {
    String[] termIdArray = termIds.split(StrUtil.COMMA);
    this.termService.deleteTerm(termIdArray);
  }

}
