package com.oes.gateway.enhance.service.impl;

import cn.hutool.core.util.StrUtil;
import com.oes.common.core.entity.QueryParam;
import com.oes.common.core.util.DateUtil;
import com.oes.gateway.enhance.entity.BlockLog;
import com.oes.gateway.enhance.mapper.BlockLogMapper;
import com.oes.gateway.enhance.service.BlockLogService;
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
public class BlockLogServiceImpl implements BlockLogService {

  private BlockLogMapper blockLogMapper;
  private ReactiveMongoTemplate template;

  @Autowired(required = false)
  public void setBlockLogMapper(BlockLogMapper blockLogMapper) {
    this.blockLogMapper = blockLogMapper;
  }

  @Autowired(required = false)
  public void setTemplate(ReactiveMongoTemplate template) {
    this.template = template;
  }

  @Override
  public Mono<BlockLog> create(BlockLog blockLog) {
    blockLog.setCreateTime(
        DateUtil.formatFullTime(LocalDateTime.now(), DateUtil.FULL_TIME_SPLIT_PATTERN));
    blockLog.setLocation(AddressUtil.getCityInfo(blockLog.getIp()));
    return blockLogMapper.insert(blockLog);
  }

  @Override
  public Flux<BlockLog> delete(String ids) {
    String[] idArray = StrUtil.splitToArray(ids, StrUtil.C_COMMA);
    return blockLogMapper.deleteByIdIn(Arrays.asList(idArray));
  }

  @Override
  public Flux<BlockLog> findPages(QueryParam request, BlockLog blockLog) {
    Query query = getQuery(blockLog);
    return PageableExecutionUtil.getPages(query, request, BlockLog.class, template);
  }

  @Override
  public Mono<Long> findCount(BlockLog blockLog) {
    Query query = getQuery(blockLog);
    return template.count(query, BlockLog.class);
  }

  private Query getQuery(BlockLog blockLog) {
    Query query = new Query();
    Criteria criteria = new Criteria();
    if (StrUtil.isNotBlank(blockLog.getRequestMethod())) {
      criteria.and("requestMethod").is(blockLog.getRequestMethod());
    }
    if (StrUtil.isNotBlank(blockLog.getIp())) {
      criteria.and("ip").is(blockLog.getIp());
    }
    if (StrUtil.isNotBlank(blockLog.getCreateTimeFrom())
        && StrUtil.isNotBlank(blockLog.getCreateTimeTo())) {
      criteria.andOperator(
          Criteria.where("createTime").gt(blockLog.getCreateTimeFrom()),
          Criteria.where("createTime").lt(blockLog.getCreateTimeTo())
      );
    }
    if (StrUtil.isNotBlank(blockLog.getRequestUri())) {
      criteria.and("requestUri").is(blockLog.getRequestUri());
    }
    query.addCriteria(criteria);
    return query;
  }
}
