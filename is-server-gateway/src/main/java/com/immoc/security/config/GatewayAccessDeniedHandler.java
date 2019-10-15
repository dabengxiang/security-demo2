package com.immoc.security.config;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: gyc
 * @Date: 2019/10/15 11:16
 */
@Component
public class GatewayAccessDeniedHandler extends OAuth2AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) throws IOException, ServletException {
        System.out.println("3.update log  403");
        request.setAttribute("logUpdated","1");

        super.handle(request, response, authException);
    }
}
