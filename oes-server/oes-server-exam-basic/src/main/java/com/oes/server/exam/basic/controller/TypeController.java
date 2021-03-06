package com.oes.server.exam.basic.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oes.common.core.entity.R;
import com.oes.common.core.exam.entity.Type;
import com.oes.common.core.exam.entity.query.QueryTypeDto;
import com.oes.common.core.util.PageUtil;
import com.oes.server.exam.basic.service.ITypeService;
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
 * @date 2020/6/3 17:40
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("type")
public class TypeController {

  private final ITypeService typeService;

  @GetMapping
  @PreAuthorize("hasAuthority('type:view')")
  public R<Map<String, Object>> pageType(QueryTypeDto type) {
    IPage<Type> result = this.typeService.pageType(type);
    return R.ok(PageUtil.toPage(result));
  }

  @GetMapping("{typeId}")
  public R<Type> selectOne(@PathVariable Long typeId) {
    return R.ok(this.typeService.getById(typeId));
  }

  @GetMapping("options")
  public R<List<Type>> options() {
    return R.ok(this.typeService.list(null));
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
