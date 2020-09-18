package com.oes.server.exam.online.config;

import com.oes.common.netty.websocket.starter.entity.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/9/13 10:51
 */
public interface SessionConfig {

  Map<String, Session> SESSION_MAP = new ConcurrentHashMap<>();

}
