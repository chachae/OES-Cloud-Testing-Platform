package com.oes.ai.ocr.baidu.cache;

import com.oes.ai.ocr.baidu.entity.AccessTokenInfo;
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

  private static final String PREFIX = "oes:ocr:baidu:accessTokenInfo";

  @Override
  public void save(String key, AccessTokenInfo value) {
    key = key == null ? PREFIX : key;
    redisService.set(key, value, Long.parseLong(value.getExpiresIn()));
  }

  @Override
  public AccessTokenInfo get(String key) {
    key = key == null ? PREFIX : key;
    Object obj = redisService.get(key);
    return (obj instanceof AccessTokenInfo) ? (AccessTokenInfo) obj : null;
  }
}
