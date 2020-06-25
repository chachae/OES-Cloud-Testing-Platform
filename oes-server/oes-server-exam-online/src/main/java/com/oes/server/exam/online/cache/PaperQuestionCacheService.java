package com.oes.server.exam.online.cache;

import com.oes.common.core.cache.ICacheService;
import com.oes.common.core.constant.SystemConstant;
import com.oes.common.core.exam.entity.PaperQuestion;
import com.oes.common.redis.starter.service.RedisService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/24 20:46
 */
@Service
@RequiredArgsConstructor
public class PaperQuestionCacheService implements
    ICacheService<Map<String, PaperQuestion>> {

  private final RedisService redisService;

  @Override
  public void save(String key, Map<String, PaperQuestion> pqMap) {
    redisService.set(key, pqMap, SystemConstant.DEFAULT_EXPIRED);
  }

  @Override
  @SuppressWarnings("unchecked")
  public Map<String, PaperQuestion> get(String id) {
    Object obj = redisService.get(id);
    if (obj instanceof Map) {
      return (Map<String, PaperQuestion>) obj;
    }
    return null;
  }
}
