package com.atguigu.springcloud.alibaba.service.impl;

import com.atguigu.springcloud.alibaba.dao.OrderDao;
import com.atguigu.springcloud.alibaba.domain.Order;
import com.atguigu.springcloud.alibaba.service.AccountService;
import com.atguigu.springcloud.alibaba.service.OrderService;
import com.atguigu.springcloud.alibaba.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDao orderDao;
    @Resource
    private StorageService storageService;
    @Resource
    private AccountService accountService;
    @Override
    public void create(Order order) {
        log.info("==========新建订单");
        orderDao.create(order);
        log.info("==========订单微服务调用库存，做扣减");
        storageService.decrease(order.getProductId(), order.getCount());
        log.info("==========订单微服务调用库存，做扣减end");
        log.info("==========订单微服务调用账户，做扣减");
        accountService.decrease(order.getUserId(), order.getMoney());
        log.info("==========订单微服务调用账户，做扣减end");
        log.info("==========修改订单状态开始");
        orderDao.update(order.getUserId(), 0);
        log.info("==========修改订单状态结束");
        log.info("==========订单完成");
    }
}
