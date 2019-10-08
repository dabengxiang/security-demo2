package com.immoc.security;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtils {

    public  static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                if(name.equals(cookie.getName())){
                    return cookie;

                }
            }
        }
        return null;

    }


    public static Cookie  createCookie(String name, String value,int maxAge){
        Cookie cookie = new Cookie(name,value);
        cookie.setDomain("immoc.com");
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        return cookie;
    }
}
