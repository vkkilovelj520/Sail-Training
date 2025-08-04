package com.itheima.controller;

import com.itheima.common.constant.MessageConstant;
import com.itheima.common.entity.PageResult;
import com.itheima.common.entity.QueryPageBean;
import com.itheima.common.entity.Result;
import com.itheima.pojo.Order;
import com.itheima.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 预约管理
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 分页查询
     */
    @PostMapping("findPage")
    public PageResult page(@RequestBody QueryPageBean queryPageBean){
        log.info("预约管理分页查询：{}",queryPageBean.toString());

        PageResult pageResult = orderService.pageQuery(queryPageBean);
        return pageResult;
    }

    /**
     * 修改状态
     */
    @PostMapping("update")
    @PreAuthorize("hasAuthority('ORDERSETTING')")
    public Result update(@RequestBody Order order){
        return orderService.update(order);
    }
}
