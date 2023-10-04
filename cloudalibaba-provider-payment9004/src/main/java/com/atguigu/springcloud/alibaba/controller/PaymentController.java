package com.atguigu.springcloud.alibaba.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PaymentController {
    @Value("${server.port}")
    private String serverPort;
    private static final Map<Long, Payment> map = new HashMap<>();
    static {
        map.put(1L, new Payment(1L, "a"));
        map.put(2L, new Payment(2L, "b"));
        map.put(3L, new Payment(3L, "c"));
    }

    @GetMapping("/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id) {
        Payment payment = map.get(id);
        return new CommonResult<>(200, "sql query, serverPort" + serverPort, payment);
    }
}
