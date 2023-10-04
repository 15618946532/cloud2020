package com.atguigu.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.alibaba.myhandler.CustomerBlockHandler;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimitController {
    @GetMapping("/byResource")
    @SentinelResource(value = "byResource", blockHandler = "handlerException")
    public CommonResult<Payment> byResource() {
        return new CommonResult<>(200,"按资源名称限流OK", new Payment(2020L, "serial001"));
    }
    public CommonResult<Payment> handlerException(BlockException exception) {
        return new CommonResult<>(444,exception.getClass().getCanonicalName() + "\t 服务不可用");
    }
    @GetMapping("/rateLimit/customerBlockHandler")
    @SentinelResource(value = "customerBlockHandler",
            blockHandlerClass = CustomerBlockHandler.class,
            blockHandler = "customerBlockHandler")
    public CommonResult<Payment> customerBlockHandler() {
        return new CommonResult<>(200,"按自定义", new Payment(2020L, "serial003"));
    }
}
