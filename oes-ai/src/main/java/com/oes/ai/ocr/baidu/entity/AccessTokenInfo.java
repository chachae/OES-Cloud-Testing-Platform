package com.oes.ai.ocr.baidu.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/29 16:23
 */
@Data
public class AccessTokenInfo implements Serializable {

  private static final long serialVersionUID = 3305176215070806147L;

  public static final String KEY_GRANT_TYPE = "grant_type";
  public static final String KEY_CLIENT_ID = "client_id";
  public static final String KEY_CLIENT_SECRET = "client_secret";
  public static final String KEY_ACCESS_TOKEN = "access_token";


  @JsonProperty("refresh_token")
  private String refreshToken;

  @JsonProperty("expires_in")
  private String expiresIn;

  private String scope;

  @JsonProperty("session_key")
  private String sessionKey;

  @JsonProperty("access_token")
  private String accessToken;

  @JsonProperty("session_secret")
  private String sessionSecret;

}
