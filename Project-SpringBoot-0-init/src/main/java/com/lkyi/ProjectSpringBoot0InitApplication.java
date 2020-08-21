package com.lkyi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan("com.lkyi.dao")
@EnableDiscoveryClient
public class ProjectSpringBoot0InitApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectSpringBoot0InitApplication.class, args);
    }

}
