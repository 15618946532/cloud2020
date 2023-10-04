package com.atguigu.springcloud.alibaba.controller;

import com.atguigu.springcloud.alibaba.domain.CommonResult;
import com.atguigu.springcloud.alibaba.domain.Order;
import com.atguigu.springcloud.alibaba.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderController {
    @Resource
    private OrderService orderService;

    @GetMapping("/order/create")
    // 一阶段提交生成before image，after image，二阶段提交执行undo log或回滚补偿sql
    @GlobalTransactional(name = "demo_create_order", timeoutMills = 600000 ,rollbackFor = Exception.class)
    public CommonResult<Void> create(Order order) {
        orderService.create(order);
        return new CommonResult<>(200, "成功");
    }
}
