package com.oes.gateway.enhance.service.impl;

import cn.hutool.core.util.StrUtil;
import com.oes.common.core.entity.QueryParam;
import com.oes.common.core.util.DateUtil;
import com.oes.gateway.enhance.entity.RouteLog;
import com.oes.gateway.enhance.mapper.RouteLogMapper;
import com.oes.gateway.enhance.service.RouteLogService;
import com.oes.gateway.enhance.util.AddressUtil;
import com.oes.gateway.enhance.util.PageableExecutionUtil;
import java.time.LocalDateTime;
import java.util.Arrays;
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
public class RouteLogServiceImpl implements RouteLogService {

  private RouteLogMapper routeLogMapper;
  private ReactiveMongoTemplate template;

  @Autowired(required = false)
  public void setRouteLogMapper(RouteLogMapper routeLogMapper) {
    this.routeLogMapper = routeLogMapper;
  }

  @Autowired(required = false)
  public void setTemplate(ReactiveMongoTemplate template) {
    this.template = template;
  }

  @Override
  public Flux<RouteLog> findAll() {
    return routeLogMapper.findAll();
  }

  @Override
  public Mono<RouteLog> create(RouteLog routeLog) {
    routeLog.setCreateTime(
        DateUtil.formatFullTime(LocalDateTime.now(), DateUtil.FULL_TIME_SPLIT_PATTERN));
    routeLog.setLocation(AddressUtil.getCityInfo(routeLog.getIp()));
    return routeLogMapper.insert(routeLog);
  }

  @Override
  public Flux<RouteLog> delete(String ids) {
    String[] idArray = StrUtil.split(ids, StrUtil.COMMA);
    return routeLogMapper.deleteByIdIn(Arrays.asList(idArray));
  }

  @Override
  public Flux<RouteLog> findPages(QueryParam request, RouteLog routeLog) {
    Query query = getQuery(routeLog);
    return PageableExecutionUtil.getPages(query, request, RouteLog.class, template);
  }

  @Override
  public Mono<Long> findCount(RouteLog routeLog) {
    Query query = getQuery(routeLog);
    return template.count(query, RouteLog.class);
  }

  private Query getQuery(RouteLog routeLog) {
    Query query = new Query();
    Criteria criteria = new Criteria();
    if (StrUtil.isNotBlank(routeLog.getIp())) {
      criteria.and("ip").is(routeLog.getIp());
    }
    if (StrUtil.isNotBlank(routeLog.getTargetServer())) {
      criteria.and("targetServer").is(routeLog.getTargetServer());
    }
    if (StrUtil.isNotBlank(routeLog.getRequestMethod())) {
      criteria.and("requestMethod").is(routeLog.getRequestMethod().toUpperCase());
    }
    if (StrUtil.isNotBlank(routeLog.getCreateTimeFrom()) && StrUtil.isNotBlank(routeLog.getCreateTimeTo())) {
      criteria.andOperator(
          Criteria.where("createTime").gt(routeLog.getCreateTimeFrom()),
          Criteria.where("createTime").lt(routeLog.getCreateTimeTo())
      );
    }
    query.addCriteria(criteria);
    return query;
  }
}
