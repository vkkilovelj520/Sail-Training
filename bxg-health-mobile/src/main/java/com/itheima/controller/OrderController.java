package com.itheima.controller;

import com.itheima.common.constant.MessageConstant;
import com.itheima.common.constant.RedisMessageConstant;
import com.itheima.common.entity.Result;
import com.itheima.dto.OrderDTO;
import com.itheima.vo.OrderVO;
import com.itheima.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据预约id查询预约信息
     */
    @PostMapping("/findById")
    public Result findById(@RequestParam Integer id){
        log.info("根据预约id查询预约信息:{}", id);
        try {
            OrderVO orderVO = orderService.findById(id);
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS,orderVO);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
        }
    }


    /**
     * 体检预约
     * @return
     */
    @PostMapping("/submitOrder")
    public Result submitOrder(@RequestBody OrderDTO orderDTO){
        log.info("体检预约");
        try {
            String telephone = orderDTO.getTelephone();
            String validateCodeInRedis = (String) redisTemplate.opsForValue().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
            String validateCode = orderDTO.getValidateCode();
            // 将用户输入的验证码与redis中的验证码比对
            if (validateCodeInRedis == null || validateCode == null || !validateCode.equals(validateCodeInRedis)){
                // 比对失败，返回验证码输入错误
                return new Result(false, MessageConstant.VALIDATECODE_ERROR);
            }
            return orderService.submitOrder(orderDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "预约失败");
        }
    }

}
