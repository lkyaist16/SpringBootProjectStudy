package com.www.lkyi.config;

import com.www.lkyi.rabbitmq.OrderReceiver;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
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

    @Bean
    public OrderReceiver receiver() {
        return new OrderReceiver();
    }

    @Bean
    public SimpleMessageListenerContainer messageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        //队列丢失，true : 队列在容器运行时被移除，则容器停止；false : 继续尝试启动消费者，每个消费者在每次恢复尝试时都会进行3次尝试（间隔5秒）
        container.setMissingQueuesFatal(false);
        //将队列放入监听容器
        container.setQueues(queueSchedule());
        //监听通道打开，默认打开
        container.setExposeListenerChannel(true);
        //最大消费者数
        container.setMaxConcurrentConsumers(1);
        //每次接收消费者数
        container.setConcurrentConsumers(1);
        //设置确认模式手工确认
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        //设置监听通道，自定义监听器
        container.setMessageListener(receiver());

        return container;
    }


}
