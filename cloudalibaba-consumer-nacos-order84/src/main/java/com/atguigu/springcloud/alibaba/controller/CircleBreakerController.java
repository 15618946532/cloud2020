package com.atguigu.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.atguigu.springcloud.alibaba.service.PaymentService;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class CircleBreakerController {
    private static final String SERVICE_URL = "http://nacos-payment-provider";

    @Resource
    private RestTemplate restTemplate;

    @RequestMapping("/consumer/fallback/{id}")
    @SentinelResource(value = "fallback",
            fallback = "handlerFallback",
            blockHandler = "blockHandler",
            exceptionsToIgnore = {IllegalArgumentException.class})
    public CommonResult<?> fallback(@PathVariable("id") Long id) {
        CommonResult<?> result = restTemplate.getForObject(SERVICE_URL + "/paymentSQL/" + id, CommonResult.class, id);
        if (id == 4) {
            throw new IllegalArgumentException("参数非法");
        } else if (result == null || result.getData() == null) {
            throw new NullPointerException("ID没有对应记录");
        }
        return result;
    }

    public CommonResult<?> handlerFallback(Long id, Throwable e) {
        return new CommonResult<>(444, "兜底异常" + e.getMessage(), new Payment(id, "null"));
    }
    public CommonResult<?> blockHandler(Long id, Throwable e) {
        return new CommonResult<>(445, "限流异常" + e.getMessage(), new Payment(id, "null"));
    }

    @Resource
    private PaymentService paymentService;
    @RequestMapping("/consumer/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id) {
        return paymentService.paymentSQL(id);
    }
}
