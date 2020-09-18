package com.oes.common.core.exam.constant;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/9/9 12:10
 */
public interface SocketConstant {

  String WEBSOCKET_STR = "websocket";
  String UPGRADE_STR = "Upgrade";
  int OK_CODE = 200;

  String HTTP_CODEC = "http-codec";
  String AGGREGATOR = "aggregator";
  String HTTP_CHUNKED = "http-chunked";
  String HANDLER = "handler";
  int MAX_CONTENT_LENGTH = 65536;
  int PORT = 8989;

  String WEB_SOCKET_URL = "ws://localhost:" + PORT + "/ws";

  //订阅者列表
  String IM_QUEUE_CHANNLID = "im-queue-channlid";
}
