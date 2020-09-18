package com.oes.server.exam.online.handler;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.oes.common.netty.websocket.starter.annotation.BeforeHandshake;
import com.oes.common.netty.websocket.starter.annotation.OnClose;
import com.oes.common.netty.websocket.starter.annotation.OnError;
import com.oes.common.netty.websocket.starter.annotation.OnMessage;
import com.oes.common.netty.websocket.starter.annotation.OnOpen;
import com.oes.common.netty.websocket.starter.annotation.RequestParam;
import com.oes.common.netty.websocket.starter.annotation.ServerEndpoint;
import com.oes.common.netty.websocket.starter.entity.Message;
import com.oes.common.netty.websocket.starter.entity.Session;
import com.oes.server.exam.online.cache.IRedisTopicMessage;
import com.oes.server.exam.online.config.SessionConfig;
import com.oes.server.exam.online.constant.MsgTypeConstant;
import com.oes.server.exam.online.constant.SessionConstant;
import com.oes.server.exam.online.entity.OnlineExamUser;
import com.oes.server.exam.online.service.IExamOnlineInfoService;
import io.netty.handler.codec.http.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author chachae
 * @date 2020/9/13 20:57
 * @version v1.0
 */
@Slf4j
@ServerEndpoint(path = "/ws-exam-online/monitor", port = "${ws.port}")
public class OESWebSocketHandler implements IRedisTopicMessage {

  private static IExamOnlineInfoService examOnlineInfoService;

  @Autowired
  private void setIExamOnlineInfoService(IExamOnlineInfoService examOnlineInfoService) {
    OESWebSocketHandler.examOnlineInfoService = examOnlineInfoService;
  }

  /**
   * 鉴权
   */
  @BeforeHandshake
  public void handshake(Session session, HttpHeaders headers, @RequestParam String username) {
    if (username == null) {
      session.close();
    } else {
      session.setSubprotocols("stomp");
    }
  }

  @OnOpen
  public void onOpen(Session session, HttpHeaders headers, @RequestParam String username, @RequestParam String fullName, @RequestParam String paperId) {
    session.setAttribute(SessionConstant.USERNAME, username);
    session.setAttribute(SessionConstant.FULLNAME, fullName);
    // 本地实例保存
    SessionConfig.SESSION_MAP.putIfAbsent(username, session);
    // 同一个考生不能在同一时间段进入不同的考试
    if (paperId != null && !examOnlineInfoService.checkOnlineUser(username, fullName, paperId)) {
      session.setAttribute(SessionConstant.PAPERID, paperId);
      // 存储在线的考生信息
      examOnlineInfoService.saveInfo(username, fullName, paperId);
    }
  }

  @OnClose
  public void onClose(Session session) {
    Object username = session.getAttribute(SessionConstant.USERNAME);
    Object fullName = session.getAttribute(SessionConstant.FULLNAME);
    if (username instanceof String && fullName instanceof String) {
      SessionConfig.SESSION_MAP.remove(username);
      examOnlineInfoService.removeInfo(session.getAttribute(SessionConstant.PAPERID), new OnlineExamUser((String) username, (String) fullName));
    }
  }

  @OnError
  public void onError(Session session, Throwable throwable) {
    onClose(session);
    log.error(throwable.getMessage());
  }

  @OnMessage
  public void onMessage(Session session, String message) {
    if (StrUtil.isNotBlank(message)) {
      receiveMessage(message);
    }
  }

  /**
   * 接收广播消息
   *
   * @param message 消息 JSON
   */
  @Override
  public void receiveMessage(String message) {
    Message msg = JSON.parseObject(message, Message.class);
    if (msg != null && SessionConfig.SESSION_MAP.get(msg.getToId()) != null) {
      handlerMessage(msg, SessionConfig.SESSION_MAP.get(msg.getToId()));
    }
  }

  private void handlerMessage(Message message, Session session) {
    switch (message.getCommand()) {
      case MsgTypeConstant.CMD:
        handleCmd(message, session);
        break;
      case MsgTypeConstant.MESSAGE:
      case MsgTypeConstant.ANSWER:
      case MsgTypeConstant.OFFER:
      case MsgTypeConstant.CANDIDATE:
      case MsgTypeConstant.HEART:
        handleMsg(message, session);
        break;
      default:
        break;
    }
  }

  /**
   * cmd 命令
   *
   * @param message 消息
   * @param session session
   */
  private void handleCmd(Message message, Session session) {
    String fromId = message.getFromId();
    message.setFromId(message.getToId());
    message.setToId(fromId);
    session.sendText(JSON.toJSONString(message));
  }

  private void handleMsg(Message message, Session session) {
    session.sendText(JSON.toJSONString(message));
  }

}