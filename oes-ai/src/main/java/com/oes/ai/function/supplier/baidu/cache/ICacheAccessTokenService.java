package com.oes.ai.function.supplier.baidu.cache;

import com.oes.ai.function.ocr.baidu.entity.AccessTokenInfo;
import com.oes.common.core.cache.ICacheService;
import com.oes.common.redis.starter.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * AccessToken 缓存接口
 *
 * @author chachae
 * @version v1.0
 * @date 2020/6/29 15:52
 */
@Service
@RequiredArgsConstructor
public class ICacheAccessTokenService implements ICacheService<AccessTokenInfo> {

  private final RedisService redisService;

  private static final String PREFIX = "oes:ai:baidu:accessTokenInfo";

  @Override
  public void save(String key, AccessTokenInfo value) {
    redisService.set(PREFIX, value, Long.parseLong(value.getExpiresIn()));
  }

  @Override
  public AccessTokenInfo get(String key) {
    Object obj = redisService.get(PREFIX);
    return (obj instanceof AccessTokenInfo) ? (AccessTokenInfo) obj : null;
  }
}
