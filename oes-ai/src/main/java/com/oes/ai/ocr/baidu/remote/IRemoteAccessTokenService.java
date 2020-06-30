package com.oes.ai.ocr.baidu.remote;

import com.oes.ai.ocr.baidu.entity.AccessTokenInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/29 15:55
 */
@FeignClient(name = "BaiduAccessToken", url = "${oes.ocr.baidu.auth-url}")
public interface IRemoteAccessTokenService {

  /**
   * 获取应用AccessToken
   *
   * @return AccessToken 信息
   */
  @PostMapping
  AccessTokenInfo getAccessToken(@RequestParam("grant_type") String grantType,
      @RequestParam("client_id") String apiKey, @RequestParam("client_secret") String secretKey);

}
