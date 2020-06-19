package com.oes.gateway.enhance.service.impl;

import cn.hutool.core.util.StrUtil;
import com.oes.common.core.entity.QueryParam;
import com.oes.common.core.util.DateUtil;
import com.oes.gateway.enhance.entity.RateLimitLog;
import com.oes.gateway.enhance.mapper.RateLimitLogMapper;
import com.oes.gateway.enhance.service.RateLimitLogService;
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
public class RateLimitLogServiceImpl implements RateLimitLogService {

  private RateLimitLogMapper rateLimitLogMapper;
  private ReactiveMongoTemplate template;

  @Autowired(required = false)
  public void setRateLimitLogMapper(RateLimitLogMapper rateLimitLogMapper) {
    this.rateLimitLogMapper = rateLimitLogMapper;
  }

  @Autowired(required = false)
  public void setTemplate(ReactiveMongoTemplate template) {
    this.template = template;
  }

  @Override
  public Mono<RateLimitLog> create(RateLimitLog rateLimitLog) {
    rateLimitLog.setCreateTime(
        DateUtil.formatFullTime(LocalDateTime.now(), DateUtil.FULL_TIME_SPLIT_PATTERN));
    rateLimitLog.setLocation(AddressUtil.getCityInfo(rateLimitLog.getIp()));
    return rateLimitLogMapper.insert(rateLimitLog);
  }

  @Override
  public Flux<RateLimitLog> delete(String ids) {
    String[] idArray = StrUtil.splitToArray(ids, StrUtil.C_COMMA);
    return rateLimitLogMapper.deleteByIdIn(Arrays.asList(idArray));
  }

  @Override
  public Flux<RateLimitLog> findPages(QueryParam request, RateLimitLog rateLimitLog) {
    Query query = getQuery(rateLimitLog);
    return PageableExecutionUtil.getPages(query, request, RateLimitLog.class, template);
  }

  @Override
  public Mono<Long> findCount(RateLimitLog rateLimitLog) {
    Query query = getQuery(rateLimitLog);
    return template.count(query, RateLimitLog.class);
  }

  private Query getQuery(RateLimitLog rateLimitLog) {
    Query query = new Query();
    Criteria criteria = new Criteria();

    if (StrUtil.isNotBlank(rateLimitLog.getIp())) {
      criteria.and("ip").is(rateLimitLog.getIp());
    }
    if (StrUtil.isNotBlank(rateLimitLog.getRequestMethod())) {
      criteria.and("requestMethod").is(rateLimitLog.getRequestMethod());
    }
    if (StrUtil.isNotBlank(rateLimitLog.getRequestUri())) {
      criteria.and("requestUri").is(rateLimitLog.getRequestUri());
    }
    if (StrUtil.isNotBlank(rateLimitLog.getCreateTimeFrom())
        && StrUtil.isNotBlank(rateLimitLog.getCreateTimeTo())) {
      criteria.andOperator(
          Criteria.where("createTime").gt(rateLimitLog.getCreateTimeFrom()),
          Criteria.where("createTime").lt(rateLimitLog.getCreateTimeTo())
      );
    }
    query.addCriteria(criteria);
    return query;
  }
}
