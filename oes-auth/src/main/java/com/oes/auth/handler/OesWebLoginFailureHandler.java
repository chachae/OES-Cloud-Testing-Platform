package com.oes.auth.handler;

import com.oes.common.core.entity.R;
import com.oes.common.core.util.HttpUtil;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * @author MrBird
 */
@Component
public class OesWebLoginFailureHandler implements AuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(
      HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
      AuthenticationException exception) throws IOException {
    String message;
    if (exception instanceof BadCredentialsException) {
      message = "用户名或密码错误！";
    } else if (exception instanceof LockedException) {
      message = "用户已被锁定！";
    } else {
      message = "认证失败，请联系网站管理员！";
    }
    HttpUtil.makeFailureResponse(httpServletResponse, R.fail(message));
  }
}
