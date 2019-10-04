package com.immoc.security;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.sun.net.httpserver.Headers;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
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

    private RestTemplate restTemplate = new RestTemplate();

    public Object run() throws ZuulException {
        //1.首先判断session里有没有token，

        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        HttpSession session = request.getSession();
        TokenInfo tokenInfo = (TokenInfo) session.getAttribute("token");



        //2.1有的话把token放到Authorization这里
        if(tokenInfo!=null) {

            //判断access_token 有没有过期，过期的话，就刷新一下token

            if(tokenInfo.isExpires()){

                try {
                    String url = "http://gateway.immoc.com:7070/token/oauth/token";
//
                    MultiValueMap params = new LinkedMultiValueMap();
                    params.add("grant_type","refresh_token");
                    params.add("refresh_token",tokenInfo.getRefresh_token());


                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    headers.setBasicAuth("admin", "123456");
                    HttpEntity httpEntity = new HttpEntity(params,headers);
//
                    ResponseEntity<TokenInfo> exchange = restTemplate.exchange(url, HttpMethod.POST, httpEntity, TokenInfo.class);

                    tokenInfo = (TokenInfo) exchange.getBody();

                    session.setAttribute("token",tokenInfo.init());
                } catch (Exception e) {
                    currentContext.getResponse().setContentType("application/json");
                    currentContext.setResponseStatusCode(500);
                    currentContext.setResponseBody("{\"message\":\"refresh fail\"}");
                    currentContext.setSendZuulResponse(false);
                    return null;
                }


            }


            currentContext.addZuulRequestHeader("Authorization" ,"bearer " + tokenInfo.getAccess_token());
        }

        return null;







    }
}
