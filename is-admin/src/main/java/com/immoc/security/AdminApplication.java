package com.immoc.security;

import com.immoc.security.utils.ResultVO;
import com.immoc.security.utils.ResultVOUtil;
import com.sun.net.httpserver.Headers;
import jdk.nashorn.internal.parser.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.EnableZuulServer;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@SpringBootApplication
@EnableZuulProxy
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class,args);

    }

    private RestTemplate restTemplate = new RestTemplate();


    @PostMapping("/login")
    public  void login(@RequestBody Credentials credentials, HttpServletRequest request){

        String url = "http://127.0.0.1:7070/token/oauth/token";


        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth("admin","123456");

        MultiValueMap<String, String> params = new LinkedMultiValueMap();
        params.set("username",credentials.getUsername());
        params.set("password",credentials.getPassword());
        params.set("grant_type","password");

        HttpEntity httpEntity = new HttpEntity(params,httpHeaders);

        ResponseEntity<TokenInfo> exchange = restTemplate.exchange(url, HttpMethod.POST, httpEntity, TokenInfo.class);

        HttpSession session = request.getSession();
        session.setAttribute("token",exchange.getBody());

    }


    @PostMapping("/layout")
    public  void login(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();

    }

    @PostMapping("/me")
    public  ResultVO me(HttpServletRequest request){
        HttpSession session = request.getSession();
        TokenInfo token = (TokenInfo) session.getAttribute("token");
        return ResultVOUtil.success(token);

    }



    @GetMapping("/oauth/callback")
    public void callback(@RequestParam(value = "code",required = false) String code,
                         HttpServletRequest request , HttpServletResponse response) throws IOException {
        if(code!=null){
            System.out.println(code);
            String url = "http://127.0.0.1:7070/token/oauth/token";

            MultiValueMap params = new LinkedMultiValueMap();
            params.add("grant_type","authorization_code");

            params.add("code" ,code );

            params.add("redirect_uri","http://127.0.0.1:8070/oauth/callback");

//            params.add("client_id","admin");
//
//            params.add("client_secret","123456");


            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setBasicAuth("admin", "123456");
            HttpEntity httpEntity = new HttpEntity(params,headers);

            ResponseEntity<TokenInfo> exchange = restTemplate.exchange(url, HttpMethod.POST, httpEntity, TokenInfo.class);
            System.out.println(exchange.getBody());

            HttpSession session = request.getSession();
            session.setAttribute("token",exchange.getBody());
            response.sendRedirect("http://127.0.0.1:8000");

        }




    }


}