package com.immoc.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

/**
 * @Author: gyc
 * @Date: 2019/10/15 9:03
 */
@Component
public class GatewayMethodSecurityExpressionHandler extends OAuth2WebSecurityExpressionHandler {

    @Autowired
    private PermissionService permissionService;

    @Override
    protected StandardEvaluationContext createEvaluationContextInternal(Authentication authentication, FilterInvocation invocation) {
        StandardEvaluationContext ec = super.createEvaluationContextInternal(authentication, invocation);
        ec.setVariable("permissionService", permissionService);
        return ec;
    }
}
