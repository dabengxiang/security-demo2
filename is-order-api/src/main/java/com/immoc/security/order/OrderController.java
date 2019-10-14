package com.immoc.security.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("order")
public class OrderController {


    @Autowired
    public RestTemplate restTemplate ;
    //@AuthenticationPrincipal 能获取用户名
    @PostMapping("/save")
    public OrderInfo save(OrderInfo orderInfo, @RequestHeader String username){

        System.out.println(username);
        return orderInfo;
    }


    @GetMapping("/get/{id}")
    public OrderInfo save(@PathVariable String id ,@RequestHeader("username") String username){

        System.out.println(id);
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId("2");
        orderInfo.setName(username);
        return orderInfo;
    }
//
//
//    @GetMapping("/getUserId/{id}")
//    public OrderInfo getUserId(@PathVariable String id ,@AuthenticationPrincipal(expression = "#this.userId")String userId){
//
//        OrderInfo orderInfo = new OrderInfo();
//        orderInfo.setId(userId);
//        return orderInfo;
//    }


    @GetMapping("/me")
    public String me(@AuthenticationPrincipal String username){
        return username;

    }

    @GetMapping("/getOrder/{orderId}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PreAuthorize("#oauth2.hasScope('write')")
    public OrderInfo getOrder(@AuthenticationPrincipal String username,@PathVariable String orderId){
        String price = restTemplate.getForObject("http://127.0.0.1:9082/price/getPrice/" + orderId, String.class);
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId("2");
        orderInfo.setName("abc");
        orderInfo.setPrice(price);
        return orderInfo;


    }

}

