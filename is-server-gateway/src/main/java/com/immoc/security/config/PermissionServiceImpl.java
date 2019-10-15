package com.immoc.security.config;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.http.AccessTokenRequiredException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: gyc
 * @Date: 2019/10/14 17:42
 */
@Service
public class PermissionServiceImpl implements  PermissionService {
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {


        if(authentication instanceof  AnonymousAuthenticationToken){
            throw  new AccessTokenRequiredException(null);
        }

        System.out.println("2 authorize");
        System.out.println(ReflectionToStringBuilder.toString(authentication));
        return RandomUtils.nextInt() % 2 == 0;
    }
}
