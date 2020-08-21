package com.lkyi.demo;

public class HelloService {

     HelloProperties helloProperties;

     public String sayHello(String name) {
         return helloProperties.getPrefix() + "-" + name + helloProperties.getSuffix();
     }

    public void setHelloProperties(HelloProperties helloProperties) {
        this.helloProperties = helloProperties;
    }
}
