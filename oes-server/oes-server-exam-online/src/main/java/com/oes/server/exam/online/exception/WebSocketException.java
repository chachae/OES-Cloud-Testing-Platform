package com.oes.server.exam.online.exception;

/**
 * WebSocket 异常
 *
 * @author chachae
 * @version v1.0
 * @date 2020/9/9 13:48
 */
public class WebSocketException extends RuntimeException {

  public WebSocketException(String message) {
    super(message);
  }
}
