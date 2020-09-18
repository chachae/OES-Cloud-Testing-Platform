package com.oes.common.netty.websocket.starter.support;

import static com.oes.common.netty.websocket.starter.entity.PojoEndpointServer.SESSION_KEY;

import com.oes.common.netty.websocket.starter.entity.Session;
import io.netty.channel.Channel;
import org.springframework.core.MethodParameter;

public class SessionMethodArgumentResolver implements MethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return Session.class.isAssignableFrom(parameter.getParameterType());
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, Channel channel, Object object) throws Exception {
    return channel.attr(SESSION_KEY).get();
  }
}
