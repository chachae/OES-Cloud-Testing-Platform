package com.oes.gateway.enhance.controller;

import com.oes.common.core.entity.QueryParam;
import com.oes.gateway.enhance.entity.BlackList;
import com.oes.gateway.enhance.service.BlackListService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author chachae
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("route/auth/blackList")
public class BlackListController {

  private final BlackListService blackListService;

  @GetMapping("data")
  public Flux<BlackList> findUserPages(QueryParam request, BlackList blackList) {
    return blackListService.findPages(request, blackList);
  }

  @GetMapping("count")
  public Mono<Long> findUserCount(BlackList blackList) {
    return blackListService.findCount(blackList);
  }

  @GetMapping("exist")
  public Flux<BlackList> findByCondition(String ip, String requestUri, String requestMethod) {
    return blackListService.findByCondition(ip, requestUri, requestMethod);
  }

  @PostMapping
  @PreAuthorize("hasAuthority('admin')")
  public Mono<BlackList> createBlackList(BlackList blackList) {
    return blackListService.create(blackList);
  }

  @PutMapping
  @PreAuthorize("hasAuthority('admin')")
  public Mono<BlackList> updateBlackList(BlackList blackList) {
    return blackListService.update(blackList);
  }

  @DeleteMapping
  @PreAuthorize("hasAuthority('admin')")
  public Flux<BlackList> deleteBlackList(String ids) {
    return blackListService.delete(ids);
  }

}
