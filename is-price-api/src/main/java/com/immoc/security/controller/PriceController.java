package com.immoc.security.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: gyc
 * @Date: 2019/10/14 15:28
 */
@RestController
@RequestMapping("/price")
public class PriceController {


    @GetMapping("/getPrice/{id}")
    public String price(@PathVariable String id , @AuthenticationPrincipal String username){
        System.out.println(username);
        return  id;
    }
}
