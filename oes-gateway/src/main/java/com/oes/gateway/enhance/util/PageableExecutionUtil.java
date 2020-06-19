package com.oes.gateway.enhance.util;

import cn.hutool.core.util.StrUtil;
import com.oes.common.core.constant.SystemConstant;
import com.oes.common.core.entity.QueryParam;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;

/**
 * @author chachae
 */
public class PageableExecutionUtil {

  public static <OES> Flux<OES> getPages(Query query, QueryParam request, Class<OES> clazz,
      ReactiveMongoTemplate template) {
    Sort sort = Sort.by("id").descending();
    if (StrUtil.isNotBlank(request.getField()) && StrUtil.isNotBlank(request.getOrder())) {
      sort = SystemConstant.ORDER_ASC.equals(request.getOrder()) ?
          Sort.by(request.getField()).ascending() :
          Sort.by(request.getField()).descending();
    }
    Pageable pageable = PageRequest
        .of(request.getPageNum().intValue(), request.getPageSize().intValue(), sort);
    return template.find(query.with(pageable), clazz);
  }
}
