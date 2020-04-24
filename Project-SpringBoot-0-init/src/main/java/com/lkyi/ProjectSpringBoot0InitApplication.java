package com.lkyi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lkyi.dao")
public class ProjectSpringBoot0InitApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectSpringBoot0InitApplication.class, args);
    }

}
