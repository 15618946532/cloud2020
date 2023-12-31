package com.atguigu.springcloud.alibaba.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class FlowLimitController {
    @GetMapping("/testA")
    public String testA() {
        return "------testA";
    }
    @GetMapping("/testB")
    public String testB() {
        log.info("=======" + Thread.currentThread().getName() + "\t...testB");
        return "------testB";
    }
    @GetMapping("/testD")
    public String testD() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e){
            e.printStackTrace();
        }
        log.info("=======" + Thread.currentThread().getName() + "\t...testD");
        return "------testD";
    }
}
