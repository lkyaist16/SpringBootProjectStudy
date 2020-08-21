package com.www.lkyi;

import com.www.lkyi.domain.Order;
import com.www.lkyi.rabbitmq.OrderSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootRabbitmqProducerApplicationTests {

    @Autowired
    private OrderSender orderSender;

    @Test
    void contextLoads() {
        orderSender.sendOrder(new Order().setId("1").setMessageId("test-order-message-id").setName("test-order-name"));
    }

}
