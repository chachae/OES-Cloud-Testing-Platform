package com.oes.server.system.controller;

import cn.hutool.core.util.StrUtil;
import com.oes.common.core.entity.R;
import com.oes.common.core.entity.system.Announce;
import com.oes.common.core.entity.system.AnnounceContent;
import com.oes.common.core.entity.system.query.AnnounceQueryDto;
import com.oes.common.core.util.PageUtil;
import com.oes.server.system.service.IAnnounceContentService;
import com.oes.server.system.service.IAnnounceService;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统公告表(Announce)表控制层
 *
 * @author chachae
 * @since 2020-07-06 10:40:32
 */
@Slf4j
@Validated
@RestController
@RequestMapping("announce")
@RequiredArgsConstructor
public class AnnounceController {

  private final IAnnounceService announceService;
  private final IAnnounceContentService announceContentService;

  @GetMapping
  public R<Map<String, Object>> pageAnnounce(AnnounceQueryDto announce) {
    return R.ok(PageUtil.toPage(announceService.pageAnnounce(announce)));
  }

  @GetMapping("content/{contentId}")
  public R<AnnounceContent> gtAnnounceContent(@PathVariable Long contentId) {
    return R.ok(announceContentService.getAnnounceContent(contentId));
  }

  @PostMapping
  public void addAnnounce(@Valid Announce announce, @Valid AnnounceContent announceContent) {
    announceContent = this.announceContentService.addAnnounceContent(announceContent);
    announce.setContentId(announceContent.getContentId());
    this.announceService.addAnnounce(announce);
  }

  @PutMapping
  public void updateAnnounce(@Valid Announce announce, @Valid AnnounceContent announceContent) {
    if (announce != null) {
      this.announceService.updateAnnounce(announce);
    }
    if (announceContent != null) {
      this.announceContentService.updateAnnounceContent(announceContent);
    }
  }

  @PutMapping("{announceId}")
  public void updateAnnounceStatus(@PathVariable @NotNull(message = "{required}") Long announceId,
      @NotNull(message = "{required}") Integer status) {
    Announce announce = new Announce();
    announce.setAnnounceId(announceId);
    announce.setStatus(status);
    this.announceService.updateAnnounce(announce);
  }

  @DeleteMapping("{announceIds}")
  public void deleteAnnounce(@PathVariable @NotBlank(message = "{required}") String announceIds) {
    List<String> ids = StrUtil.split(announceIds, StrUtil.C_COMMA);
    this.announceService.deleteAnnounce(ids);
  }
}