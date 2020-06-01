package com.oes.auth.handler;

import com.oes.auth.exception.CaptchaException;
import com.oes.common.core.entity.R;
import com.oes.common.core.handler.BaseExceptionHandler;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 服务异常处理
 *
 * @author chachae
 * @since 2020/4/24 21:25
 */

@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends BaseExceptionHandler {

  /**
   * 验证码异常
   */
  @ExceptionHandler(CaptchaException.class)
  public R<String> captchaExceptionException(CaptchaException e) {
    return R.fail(e.getMessage());
  }

}
