package com.itheima.controller;

import com.itheima.common.constant.MessageConstant;
import com.itheima.common.constant.RedisMessageConstant;
import com.itheima.common.entity.Result;
import com.itheima.common.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 *  发送验证码
 */
@RestController
@Slf4j
@RequestMapping("/validatecode")
public class ValidateCodeController {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 用户在线体检预约发送验证码
     * @param telephone
     * @return
     */
    @PostMapping("/send4Order")
    public Result send4Order(String telephone){
        //随机生成四位数验证码
        Integer validateCode = ValidateCodeUtils.generateValidateCode(4);
        String key = telephone + RedisMessageConstant.SENDTYPE_ORDER;
        try {
            log.info("体检预约发送验证码为：{}", validateCode);
            // 将验证码保存到redis中
            redisTemplate.opsForValue().set(key, validateCode.toString(),5, TimeUnit.MINUTES);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

    /**
     * 手机号快速登录发送验证码
     */
    @GetMapping("send4Login")
    public Result send4Login(String telephone){
        //随机生成四位数验证码
        Integer validateCode = ValidateCodeUtils.generateValidateCode(6);
        String key = telephone + RedisMessageConstant.SENDTYPE_LOGIN;

        try {
            log.info("手机号快速登录发送验证码为：{}", validateCode);
            // 将验证码保存到redis中
            redisTemplate.opsForValue().set(key, validateCode.toString(),5, TimeUnit.MINUTES);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }
}
