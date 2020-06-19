package com.oes.auth.handler;

import com.oes.common.core.entity.R;
import com.oes.common.core.util.HttpUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

/**
 * @author MrBird
 */
@Slf4j
@Component
public class OesWebLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  private final RequestCache requestCache = new HttpSessionRequestCache();

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws ServletException, IOException {
    SavedRequest savedRequest = requestCache.getRequest(request, response);
    HttpSession session = request.getSession(false);
    if (session != null) {
      Object attribute = session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
      log.info("跳转到登录页的地址为: {}", attribute);
    }
    if (HttpUtil.isAjaxRequest(request)) {
      if (savedRequest == null) {
        HttpUtil.makeFailureResponse(response, R.fail("请通过授权码模式跳转到该页面"));
        return;
      }
      HttpUtil.makeSuccessResponse(response, R.ok(savedRequest.getRedirectUrl()));
    } else {
      if (savedRequest == null) {
        super.onAuthenticationSuccess(request, response, authentication);
        return;
      }
      clearAuthenticationAttributes(request);
      getRedirectStrategy().sendRedirect(request, response, savedRequest.getRedirectUrl());
    }
  }
}
