package com.oes.common.security.starter.interceptor;

import com.oes.common.core.entity.R;
import com.oes.common.core.constant.GatewayConstant;
import com.oes.common.core.util.HttpUtil;
import com.oes.common.security.starter.properties.OesCloudSecurityProperties;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.util.Base64Utils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author chachae
 */
public class OesServerProtectInterceptor implements HandlerInterceptor {

  private OesCloudSecurityProperties properties;


  public void setProperties(OesCloudSecurityProperties properties) {
    this.properties = properties;
  }

  @Override
  public boolean preHandle(@NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response, @NonNull Object handler) throws IOException {
    if (!properties.getOnlyFetchByGateway()) {
      return true;
    }
    String token = request.getHeader(GatewayConstant.TOKEN_HEADER);
    String gatewayToken = new String(
        Base64Utils.encode(GatewayConstant.TOKEN_VALUE.getBytes()));
    if (gatewayToken.equals(token)) {
      return true;
    } else {
      HttpUtil.makeJsonResponse(response, HttpServletResponse.SC_FORBIDDEN,
          R.fail("请通过网关获取资源"));
      return false;
    }
  }
}
