package com.oes.server.examination.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oes.common.core.entity.QueryParam;
import com.oes.common.core.entity.R;
import com.oes.common.core.util.PageUtil;
import com.oes.server.examination.entity.system.Type;
import com.oes.server.examination.service.ITypeService;
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
 * @date 2020/6/3 17:40
 */
@Slf4j
@Validated
@RestController
@RequestMapping("type")
@RequiredArgsConstructor
public class TypeController {

  private final ITypeService typeService;

  @GetMapping
  @PreAuthorize("hasAuthority('type:view')")
  public R<Map<String, Object>> pageType(QueryParam param, Type type) {
    IPage<Type> result = this.typeService.pageType(param, type);
    return R.ok(PageUtil.toPage(result));
  }

  @GetMapping("{typeId}")
  public R<Type> selectOne(@PathVariable Long typeId) {
    return R.ok(this.typeService.getById(typeId));
  }

  @PostMapping
  @PreAuthorize("hasAuthority('type:add')")
  public void save(@Valid Type type) {
    this.typeService.createType(type);
  }

  @PutMapping
  @PreAuthorize("hasAuthority('type:update')")
  public void update(@Valid Type type) {
    this.typeService.updateType(type);
  }

  @DeleteMapping("{typeIds}")
  @PreAuthorize("hasAuthority('type:delete')")
  public void delete(@NotBlank(message = "{required}") @PathVariable String typeIds) {
    String[] typeIdArray = typeIds.split(StrUtil.COMMA);
    this.typeService.deleteType(typeIdArray);
  }

  @GetMapping("check/{typeName}")
  public boolean checkUserName(@NotBlank(message = "{required}") @PathVariable String typeName) {
    Type type = this.typeService.getByTypeName(typeName);
    return type == null;
  }


}
