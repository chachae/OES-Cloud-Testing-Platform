package com.oes.ai.ocr.baidu.service.impl;

import com.oes.ai.ocr.baidu.cache.ICacheAccessTokenService;
import com.oes.ai.ocr.baidu.entity.AccessTokenInfo;
import com.oes.ai.ocr.baidu.properties.BaiduOcrProperties;
import com.oes.ai.ocr.baidu.remote.IRemoteAccessTokenService;
import com.oes.ai.ocr.baidu.service.IAccessTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/29 16:49
 */
@Service
@RequiredArgsConstructor
public class AccessTokenServiceImpl implements IAccessTokenService {

  private final BaiduOcrProperties properties;
  private final ICacheAccessTokenService cacheAccessTokenService;
  private final IRemoteAccessTokenService remoteAccessTokenService;

  @Override
  public String getAccessToken() {
    AccessTokenInfo info = cacheAccessTokenService.get(null);
    if (info == null) {
      info = remoteAccessTokenService
          .getAccessToken(properties.getGrantType(), properties.getApiKey(),
              properties.getSecretKey());
      // 缓存
      cacheAccessTokenService.save(null, info);
    }
    return info.getAccessToken();
  }
}
