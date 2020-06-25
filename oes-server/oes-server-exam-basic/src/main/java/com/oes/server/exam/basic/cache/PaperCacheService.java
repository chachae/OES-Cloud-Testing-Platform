package com.oes.server.exam.basic.cache;

import com.oes.common.core.cache.ICacheService;
import com.oes.common.core.exam.entity.Paper;
import com.oes.common.redis.starter.service.RedisService;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/24 20:46
 */
@Service
@RequiredArgsConstructor
public class PaperCacheService implements ICacheService<Paper> {

  private final RedisService redisService;

  @Override
  public void save(String key, Paper paper) {
    redisService.set(key, paper, Long.valueOf(paper.getMinute()), TimeUnit.MINUTES);
  }

  @Override
  public Paper get(String id) {
    Object obj = redisService.get(id);
    if (obj instanceof Paper) {
      return (Paper) obj;
    }
    return null;
  }

}
