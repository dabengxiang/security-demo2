package com.immoc.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.access.ExceptionTranslationFilter;

/**
 * @Author: gyc
 * @Date: 2019/10/14 9:46
 */
@Configuration
@EnableResourceServer
public class GatewayResourceServerConfigurer extends ResourceServerConfigurerAdapter {


    @Autowired
    private GatewayMethodSecurityExpressionHandler gatewayMethodSecurityExpressionHandler;

    @Autowired
    private GatewayAccessDeniedHandler gatewayAccessDeniedHandler;


    @Autowired
    private GatewayAuthenticationEntryPoint gatewayAuthenticationEntryPoint;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.accessDeniedHandler(gatewayAccessDeniedHandler)
                .authenticationEntryPoint(gatewayAuthenticationEntryPoint)
                .expressionHandler(gatewayMethodSecurityExpressionHandler);
    }





    public void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new GatewayAuditLogFilter(),ExceptionTranslationFilter.class);
        http.authorizeRequests().antMatchers("/token/**").permitAll().anyRequest().
                access("#permissionService.hasPermission(request, authentication)");


    }

}
