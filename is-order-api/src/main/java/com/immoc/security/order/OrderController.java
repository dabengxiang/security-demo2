package com.immoc.security.order;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")

public class OrderController {


    //@AuthenticationPrincipal 能获取用户名
    @PostMapping("/save")
    public OrderInfo save(OrderInfo orderInfo, @AuthenticationPrincipal String username){

        System.out.println(username);
        return orderInfo;
    }
}
