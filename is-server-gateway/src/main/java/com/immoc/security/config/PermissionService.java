package com.immoc.security.config;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: gyc
 * @Date: 2019/10/14 17:40
 */
public interface PermissionService {

    public boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
