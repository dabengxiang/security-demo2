package com.immoc.security.filter;

import com.immoc.security.entity.TokenInfo;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: gyc
 * @Date: 2019/9/25 10:56
 */
@Slf4j
//@Component
public class AuthorizationFilter extends ZuulFilter {
    public String filterType() {
        return "pre";
    }

    public int filterOrder() {
        return 2;
    }

    public boolean shouldFilter() {
        return true;
    }

    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();

        String requestURI = request.getRequestURI();
        if(StringUtils.startsWithIgnoreCase(requestURI,"/token")){
            return null;
        }

        if(isNeedAuth(request)){
            TokenInfo tokenInfo = (TokenInfo) request.getAttribute("tokenInfo");
            if(tokenInfo!=null && tokenInfo.isActive()){
                if(!hasPermission(tokenInfo,request)){
                    log.error("403 error");
                    this.handlerror(403,currentContext);
                }
                currentContext.addZuulRequestHeader("username", tokenInfo.getUser_name());

            }else{
                log.error("401 error");
                this.handlerror(401,currentContext);
            }



        }

        return null;
    }


    /**
     * 错误处理
     * @param status
     * @param context
     */
    private void handlerror(int status , RequestContext requestContext){
        requestContext.getResponse().setContentType("application/json");
        requestContext.setResponseStatusCode(status);
        requestContext.setResponseBody("{\"message\":\"auth fail\"}");
        requestContext.setSendZuulResponse(false);

    }


    /**
     * 是否需要审核
     * @return
     */
    private boolean isNeedAuth(HttpServletRequest request){
        return true;
    }


    private boolean hasPermission(TokenInfo tokenInfo, HttpServletRequest request) {
        return true; //RandomUtils.nextInt() % 2 == 0;
    }

}
