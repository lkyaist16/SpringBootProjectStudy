package com.lkyi.www;

import com.alibaba.cloud.nacos.registry.NacosAutoServiceRegistration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 描述:
 * tomcat部署服务时nacos的配置
 *
 * @author zhengql
 * @date 2019/7/2 14:23
 */
@Component
@RefreshScope
public class NacosConfig implements ApplicationContextAware {
    @Autowired
    private NacosAutoServiceRegistration nacosAutoServiceRegistration;

    @Value("${server.port}")
    private Integer port ;



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (nacosAutoServiceRegistration != null && port != null) {
            nacosAutoServiceRegistration.setPort(port);
            nacosAutoServiceRegistration.start();
        }
    }
}
