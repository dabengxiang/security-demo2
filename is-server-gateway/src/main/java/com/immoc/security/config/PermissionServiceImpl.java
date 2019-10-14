package com.immoc.security.config;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: gyc
 * @Date: 2019/10/14 17:42
 */
public class PermissionServiceImpl implements  PermissionService {
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        System.out.println(ReflectionToStringBuilder.toString(authentication));
        return RandomUtils.nextInt() % 2 == 0;
    }


}
