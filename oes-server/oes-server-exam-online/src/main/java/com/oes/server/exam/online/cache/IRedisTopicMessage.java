package com.oes.server.exam.online.cache;

public interface IRedisTopicMessage {

  /**
   * 接受信息
   *
   * @param message 消息 JSON
   */
  void receiveMessage(String message);
}
