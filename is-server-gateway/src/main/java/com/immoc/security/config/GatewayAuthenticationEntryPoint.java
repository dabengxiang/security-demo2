package com.immoc.security.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.http.AccessTokenRequiredException;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: gyc
 * @Date: 2019/10/15 11:20
 */
@Component
public class GatewayAuthenticationEntryPoint extends OAuth2AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        if(authException instanceof  AccessTokenRequiredException){
            System.out.println("3.update log 401");
        }else{
            System.out.println("3.add log 401");
        }

        request.setAttribute("logUpdated","1");
        super.commence(request, response, authException);
    }
}
