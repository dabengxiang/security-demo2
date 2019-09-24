package com.immoc.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @Author: gyc
 * @Date: 2019/9/24 9:59
 */
@SpringBootApplication
@EnableZuulProxy

public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class,args);

    }
}
