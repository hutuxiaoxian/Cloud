package com.haijinhan.utils.mq;

import com.haijinhan.utils.database.UserEntity;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class MqService {

    private final RabbitTemplate rabbitTemplate;

    public MqService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * 发送普通消息
     */
    public void send(String exchange, String routingKey, Object payload) {
        rabbitTemplate.convertAndSend(exchange, routingKey, payload);
    }

    /**
     * 发送延迟消息 (需安装 rabbitmq_delayed_message_exchange 插件)
     * @param delayMillis 延迟毫秒数
     */
    public void sendDelay(String exchange, String routingKey, Object payload, long delayMillis) {
        MessagePostProcessor processor = message -> {
            message.getMessageProperties().setReceivedDelayLong(delayMillis);
            return message;
        };

        rabbitTemplate.convertAndSend(exchange, routingKey, payload, processor);
    }

    /**
     * 发送消息并携带确认回调 (可靠性投递)
     */
    public void sendWithConfirm(String exchange, String routingKey, Object payload,
                                RabbitTemplate.ConfirmCallback confirmCallback) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.convertAndSend(exchange, routingKey, payload, correlationData);
    }
}
