package com.www.lkyi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "rabbitmq")
public class RabbitMqSetting {

    private String host;

    private Integer port;

    private String username;

    private String password;

    private String virtualHost;
}
