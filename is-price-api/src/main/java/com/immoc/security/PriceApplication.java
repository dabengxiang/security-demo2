package com.immoc.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @Author: gyc
 * @Date: 2019/10/14 15:26
 */
@SpringBootApplication
@EnableResourceServer
public class PriceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PriceApplication.class);
    }

}
