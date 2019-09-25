package com.immoc.security.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;

/**
 * @Author: gyc
 * @Date: 2019/9/25 13:43
 */
public class AuditFilter extends ZuulFilter {
    public String filterType() {
        return "pre";
    }

    public int filterOrder() {
        return 1;
    }

    public boolean shouldFilter() {
        return true;
    }

    public Object run() throws ZuulException {
        return null;
    }
}
