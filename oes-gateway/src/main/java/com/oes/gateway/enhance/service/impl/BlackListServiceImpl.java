package com.oes.gateway.enhance.service.impl;

import cn.hutool.core.util.StrUtil;
import com.oes.common.core.entity.QueryParam;
import com.oes.common.core.util.DateUtil;
import com.oes.gateway.enhance.entity.BlackList;
import com.oes.gateway.enhance.mapper.BlackListMapper;
import com.oes.gateway.enhance.service.BlackListService;
import com.oes.gateway.enhance.service.RouteEnhanceCacheService;
import com.oes.gateway.enhance.util.AddressUtil;
import com.oes.gateway.enhance.util.PageableExecutionUtil;
import java.time.LocalDateTime;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author chachae
 */
@Service
@RequiredArgsConstructor
public class BlackListServiceImpl implements BlackListService {


  private final RouteEnhanceCacheService routeEnhanceCacheService;
  private BlackListMapper blackListMapper;
  private ReactiveMongoTemplate template;

  @Autowired(required = false)
  public void setBlackListMapper(BlackListMapper blackListMapper) {
    this.blackListMapper = blackListMapper;
  }

  @Autowired(required = false)
  public void setTemplate(ReactiveMongoTemplate template) {
    this.template = template;
  }

  @Override
  public Flux<BlackList> findAll() {
    return blackListMapper.findAll();
  }

  @Override
  public Mono<BlackList> create(BlackList blackList) {
    blackList.setCreateTime(
        DateUtil.formatFullTime(LocalDateTime.now(), DateUtil.FULL_TIME_SPLIT_PATTERN));
    if (StrUtil.isNotBlank(blackList.getIp())) {
      blackList.setLocation(AddressUtil.getCityInfo(blackList.getIp()));
    }
    return blackListMapper.insert(blackList)
        .doOnSuccess(b -> routeEnhanceCacheService.saveBlackList(blackList));
  }

  @Override
  public Mono<BlackList> update(BlackList blackList) {
    return this.blackListMapper.findById(blackList.getId())
        .flatMap(b -> {
          routeEnhanceCacheService.removeBlackList(b);
          BeanUtils.copyProperties(blackList, b);
          return this.blackListMapper.save(b);
        }).doOnSuccess(routeEnhanceCacheService::saveBlackList);
  }

  @Override
  public Flux<BlackList> delete(String ids) {
    String[] idArray = StrUtil.splitToArray(ids, StrUtil.C_COMMA);
    return blackListMapper.deleteByIdIn(Arrays.asList(idArray))
        .doOnNext(routeEnhanceCacheService::removeBlackList);
  }

  @Override
  public Flux<BlackList> findPages(QueryParam request, BlackList blackList) {
    Query query = getQuery(blackList);
    return PageableExecutionUtil.getPages(query, request, BlackList.class, template);
  }

  @Override
  public Mono<Long> findCount(BlackList blackList) {
    Query query = getQuery(blackList);
    return template.count(query, BlackList.class);
  }

  @Override
  public Flux<BlackList> findByCondition(String ip, String requestUri, String requestMethod) {
    if (StrUtil.isBlank(ip)) {
      return blackListMapper.findByRequestUriAndRequestMethod(requestUri, requestMethod);
    }
    return blackListMapper.findByIpAndRequestUriAndRequestMethod(ip, requestUri, requestMethod);
  }

  private Query getQuery(BlackList blackList) {
    Query query = new Query();
    Criteria criteria = new Criteria();
    if (StrUtil.isNotBlank(blackList.getIp())) {
      criteria.and("ip").is(blackList.getIp());
    }
    if (StrUtil.isNotBlank(blackList.getRequestUri())) {
      criteria.and("requestUri").is(blackList.getRequestUri());
    }
    if (StrUtil.isNotBlank(blackList.getRequestMethod())) {
      criteria.and("requestMethod").is(blackList.getRequestMethod());
    }
    if (StrUtil.isNotBlank(blackList.getStatus())) {
      criteria.and("status").is(blackList.getStatus());
    }
    query.addCriteria(criteria);
    return query;
  }
}
