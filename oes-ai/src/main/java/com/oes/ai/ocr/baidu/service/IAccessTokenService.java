package com.oes.ai.ocr.baidu.service;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/29 16:48
 */
public interface IAccessTokenService {

  /**
   * 获取百度应用的 AccessToken
   *
   * @return AccessToken
   */
  String getAccessToken();

}
