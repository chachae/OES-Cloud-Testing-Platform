package com.oes.server.exam.online.config;

import com.oes.server.exam.online.cache.IRedisTopicMessage;
import com.oes.server.exam.online.constant.SocketConfigConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/9/13 21:09
 */
@Configuration
public class RedisPublishConfig {

  @Bean
  RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
      MessageListenerAdapter listenerAdapter) {
    RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    // socket 消息订阅
    container.addMessageListener(listenerAdapter, new PatternTopic(SocketConfigConstant.MESSAGE_TOPIC));
    return container;
  }

  @Bean
  MessageListenerAdapter listenerAdapter(IRedisTopicMessage receiver) {
    // 这个地方 是给messageListenerAdapter 传入一个消息接受的处理器，利用反射的方法调用receiveMessage
    return new MessageListenerAdapter(receiver, "receiveMessage");
  }

}
