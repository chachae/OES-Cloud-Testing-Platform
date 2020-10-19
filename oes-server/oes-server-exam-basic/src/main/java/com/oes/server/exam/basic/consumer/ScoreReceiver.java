package com.oes.server.exam.basic.consumer;

import com.oes.common.core.exam.constant.ScoreMqConstant;
import com.oes.common.core.exam.entity.Score;
import com.oes.server.exam.basic.service.IScoreService;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 *
 * @author chachae
 * @date 2020/10/19 19:19
 * @version v1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ScoreReceiver {

  private final IScoreService scoreService;

  //配置监听队列
  @RabbitListener(
      bindings = @QueueBinding(
          value = @Queue(value = ScoreMqConstant.MARK_SCORES_QUEUE, durable = "true"),
          exchange = @Exchange(name = ScoreMqConstant.MARK_SCORES_EXCHANGE, type = "topic"),
          key = ScoreMqConstant.MARK_SCORES_ROUTING_KEY)
  )
  @RabbitHandler
  public void onOrderMessage(@Payload Score score, @Headers Map<String, Object> headers, Channel channel) throws IOException {
    Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
    // 消费者操作
    scoreService.autoMarkScore(score);
    // 确认一条消息已经被消费
    channel.basicAck(deliveryTag, false);
  }
}

