package com.immoc.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @Author: gyc
 * @Date: 2019/10/14 9:46
 */
@Configuration
@EnableResourceServer
public class GatewayResourceServerConfigurer extends ResourceServerConfigurerAdapter {




    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/token/**").permitAll().anyRequest().authenticated()
            .;

    }

}
