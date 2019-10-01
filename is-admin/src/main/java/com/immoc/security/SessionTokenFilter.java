package com.immoc.security;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.sun.net.httpserver.Headers;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Component
public class SessionTokenFilter extends ZuulFilter {

    public String filterType() {
        return "pre";
    }

    public int filterOrder() {
        return 0;
    }

    public boolean shouldFilter() {
        return true;
    }

    public Object run() throws ZuulException {
        //1.首先判断session里有没有token，

        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        HttpSession session = request.getSession();
        TokenInfo tokenInfo = (TokenInfo) session.getAttribute("token");

        //2.1有的话把token放到Authorization这里
        if(tokenInfo!=null) {

            currentContext.addZuulRequestHeader("Authorization" ,"bearer " + tokenInfo.getAccess_token());
        }

        return null;







    }
}
