package com.www.lkyi.rabbitmq;

import com.www.lkyi.domain.Order;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendOrder(Order order) {
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(order.getMessageId());
        rabbitTemplate.convertAndSend("order-exchange",
                "queue.order",
                order,
                correlationData);

    }
}
