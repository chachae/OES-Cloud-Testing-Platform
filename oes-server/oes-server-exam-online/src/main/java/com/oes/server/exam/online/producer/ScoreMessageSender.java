package com.oes.server.exam.online.producer;

import com.oes.common.core.exam.constant.ScoreMqConstant;
import com.oes.common.core.exam.entity.Score;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @author chachae
 * @date 2020/10/19 17:10
 * @version v1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ScoreMessageSender {

  private final RabbitTemplate rabbitTemplate;
  // 回调配置
  final RabbitTemplate.ConfirmCallback confirmCallback = (correlationData, ack, cause) -> {
    assert correlationData != null;
    if (!ack) {
      log.error("成绩结算异常 : [ {} ]", cause);
    }
  };

  // 成绩结算消费消息生产
  public void sendMarkScoreMessage(Score score) {
    rabbitTemplate.setConfirmCallback(confirmCallback);
    CorrelationData correlationData = new CorrelationData(String.valueOf(score.getScoreId()));
    rabbitTemplate.convertAndSend(ScoreMqConstant.MARK_SCORES_EXCHANGE, ScoreMqConstant.MARK_SCORES_ROUTING_KEY, score, correlationData);
  }

}

