package com.www.lkyi.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@EnableConfigurationProperties({RabbitMqSetting.class})
public class RabbitMqConfig {

    @Autowired
    private RabbitMqSetting rabbitMqSetting;

    @Bean
    public ConnectionFactory connectionFactory() {
        com.rabbitmq.client.ConnectionFactory connectionFactory = new com.rabbitmq.client.ConnectionFactory();
        connectionFactory.setHost(rabbitMqSetting.getHost());
        connectionFactory.setPort(rabbitMqSetting.getPort());
        connectionFactory.setUsername(rabbitMqSetting.getUsername());
        connectionFactory.setPassword(rabbitMqSetting.getPassword());
        connectionFactory.setVirtualHost(rabbitMqSetting.getVirtualHost());
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(connectionFactory);
        //设置消息回调
        return cachingConnectionFactory;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate(@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    /**
     * AMQP管理
     */
    @Bean
    public RabbitAdmin rabbitAdmin(@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        return admin;
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("order-exchange", true, false);
    }

    @Bean
    public Queue queueSchedule() {
        //durable = true : 队列持久
        return new Queue("queue.order", true, false, false);
    }

    /**
     * 消息队列 QUEUE_SCHEDULE 与交换机绑定
     */
    @Bean
    public Binding queueScheduleBinding() {
        return BindingBuilder.bind(queueSchedule()).to(directExchange()).with("queue.order");
    }

}
