package com.oes.common.netty.websocket.starter.entity;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author chachae
 * @date 2020/9/13 21:12
 * @version v1.0
 */
public class Message implements Serializable {

  private static final long serialVersionUID = -4916539908205079687L;

  public Message() {

  }

  /**
   * 发送方id
   */
  private String fromId;

  /**
   * 接收方id
   */
  private String toId;

  /**
   * 发送文本
   */
  private transient Map<String, Object> content;

  /**
   * 消息类型
   */
  private String command;

  public String getFromId() {
    return fromId;
  }

  public void setFromId(String fromId) {
    this.fromId = fromId;
  }

  public String getToId() {
    return toId;
  }

  public void setToId(String toId) {
    this.toId = toId;
  }

  public Map<String, Object> getContent() {
    return content;
  }

  public void setContent(Map<String, Object> content) {
    this.content = content;
  }

  public String getCommand() {
    return command;
  }

  public void setCommand(String command) {
    this.command = command;
  }

  @Override
  public String toString() {
    return "Message{" +
        "fromId='" + fromId + '\'' +
        ", toId='" + toId + '\'' +
        ", content='" + content + '\'' +
        ", command='" + command + '\'' +
        '}';
  }
}
