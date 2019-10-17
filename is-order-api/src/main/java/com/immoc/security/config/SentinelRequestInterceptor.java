package com.immoc.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: gyc
 * @Date: 2019/10/17 16:16
 */

@Component
public class SentinelRequestInterceptor implements ClientHttpRequestInterceptor {



    @Value("${spring.application.name}")
    private String appname;

    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        HttpHeaders headers = httpRequest.getHeaders();
        headers.add("sentinelAppName", appname);
        return clientHttpRequestExecution.execute(httpRequest,bytes);
    }
}