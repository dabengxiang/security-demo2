package com.immoc.security.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: gyc
 * @Date: 2019/10/15 10:45
 */
public class GatewayAuditLogFilter extends OncePerRequestFilter {
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("1.add log for db");
        filterChain.doFilter(httpServletRequest,httpServletResponse);

        if(StringUtils.isBlank((CharSequence) httpServletRequest.getAttribute("logUpdated"))){
            System.out.println("3.update log  success for db");
        }



    }
}
