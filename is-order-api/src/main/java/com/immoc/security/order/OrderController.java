package com.immoc.security.order;

import com.immoc.security.server.resource.MyUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
public class OrderController {



    //@AuthenticationPrincipal 能获取用户名
    @PostMapping("/save")
    public OrderInfo save(OrderInfo orderInfo, @AuthenticationPrincipal String username){

        System.out.println(username);
        return orderInfo;
    }


    @GetMapping("/get/{id}")
    public OrderInfo save(@PathVariable String id ,@AuthenticationPrincipal MyUser user){

        System.out.println(id);
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(user.getUserId());
        orderInfo.setName(user.getUserName());
        return orderInfo;
    }


    @GetMapping("/getUserId/{id}")
    public OrderInfo getUserId(@PathVariable String id ,@AuthenticationPrincipal(expression = "#this.userId")String userId){

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(userId);
        return orderInfo;
    }


}

