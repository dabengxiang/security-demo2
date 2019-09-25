package com.immoc.security.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author: gyc
 * @Date: 2019/9/25 11:28
 */
@Data
public class TokenInfo {

    private List<String> aud;

    private String user_name;

    private List<String>  scope;

    private boolean active;

    private Long exp;

    private List<String> authorities;

    private String client_id;
}
