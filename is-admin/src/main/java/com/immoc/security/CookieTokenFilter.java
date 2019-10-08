package com.immoc.security;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
public class CookieTokenFilter extends ZuulFilter {

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext currentContext = RequestContext.getCurrentContext();


        Cookie access_token = CookieUtils.getCookie(currentContext.getRequest(),"access_token");


        String accessToken = null;

        if(access_token==null){

            Cookie refreshToken = CookieUtils.getCookie(currentContext.getRequest(),"refresh_token");
            if(refreshToken!=null){

                try {
                String url = "http://gateway.immoc.com:7070/token/oauth/token";
                MultiValueMap params = new LinkedMultiValueMap();
                params.add("grant_type","refresh_token");
                params.add("refresh_token",refreshToken.getValue());

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.setBasicAuth("admin", "123456");
                HttpEntity httpEntity = new HttpEntity(params,headers);
                ResponseEntity<TokenInfo> exchange = restTemplate.exchange(url, HttpMethod.POST, httpEntity, TokenInfo.class);
                TokenInfo tokenInfo = exchange.getBody();
                currentContext.getResponse().addCookie(CookieUtils.createCookie("access_token",tokenInfo.getAccess_token(),tokenInfo.getExpires_in().intValue()));
                    accessToken = tokenInfo.getAccess_token();
                } catch (Exception e) {
                    currentContext.getResponse().setContentType("application/json");
                    currentContext.setResponseStatusCode(500);
                    currentContext.setResponseBody("{\"message\":\"refresh fail\"}");
                    currentContext.setSendZuulResponse(false);
                    return null;
                }

            }

        }else{
            accessToken = access_token.getValue();
        }

        if(accessToken!=null){
            currentContext.addZuulRequestHeader("Authorization" ,"bearer " + accessToken);
        }

        return null;


    }




}
