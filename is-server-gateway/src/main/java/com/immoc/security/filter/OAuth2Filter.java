package com.immoc.security.filter;

import com.immoc.security.entity.TokenInfo;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: gyc
 * @Date: 2019/9/25 11:20
 */
//@Component
@Slf4j
public class OAuth2Filter extends ZuulFilter {

    private RestTemplate restTemplate =  new RestTemplate();;

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
        String bearer = "bearer ";
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();

        //直接取request头部，看下有没有Authorization，而且内容是bearer , 】
        // 有的话就去鉴权，如果拿到数据信息就放到request里，能不能放权都是放到授权部分
        String headerValue = request.getHeader("Authorization");

        if(StringUtils.startsWithIgnoreCase(headerValue, "bearer ")) {
            String token = StringUtils.substringAfter(headerValue,"bearer ");
            try {
                TokenInfo tokenInfo = this.checkToken(token);
                request.setAttribute("tokenInfo",tokenInfo);
            }catch (Exception e){
                log.error(e.getClass().toString(),e);
            }

        }

        return null;

    }


    public TokenInfo checkToken(String token){
        String url ="http://localhost:9090/oauth/check_token";
        MultiValueMap  multiValueMap  = new LinkedMultiValueMap();
        multiValueMap.set("token",token);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth("gateway","123456");


        HttpEntity<MultiValueMap<String,Object>> httpEntity = new HttpEntity(multiValueMap,httpHeaders);


        ResponseEntity<TokenInfo> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, TokenInfo.class);
        TokenInfo tokenInfo= responseEntity.getBody();

        System.out.println(tokenInfo);
        return tokenInfo;


    }
}
