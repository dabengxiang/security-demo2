package com.immoc.security.order;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")

public class OrderController {

    @PostMapping("/save")
    public OrderInfo save(OrderInfo orderInfo){

        return orderInfo;
    }
}
