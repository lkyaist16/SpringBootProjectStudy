package com.lkyi.www;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String testHello() {
        Map<String, String> metadata = getMetadata();

        String  version = metadata.get("version");
        return "hello";
    }
}
