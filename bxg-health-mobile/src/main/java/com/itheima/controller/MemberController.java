package com.itheima.controller;

import com.itheima.common.constant.MessageConstant;
import com.itheima.common.constant.RedisMessageConstant;
import com.itheima.common.entity.Result;
import com.itheima.dto.LoginDTO;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RequestMapping("/member")
@RestController
@Slf4j
public class MemberController {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MemberService memberService;

    /**
     * 手机验证码登录
     * @param loginDTO
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginDTO loginDTO){
        log.info("手机验证码登录：{}", loginDTO);

        // 输入的验证码
        String validateCode = loginDTO.getValidateCode();
        String telephone = loginDTO.getTelephone();
        String validateCodeInRedis = (String) redisTemplate.opsForValue().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);

        if (validateCodeInRedis != null && validateCode != null && validateCode.equals(validateCodeInRedis)){
            // 验证码输入正确
            // 判断当前用户是否为会员
            Member member = memberService.findByTelephone(telephone);
            if (member == null){
                // 不是会员，自动完成注册
                member = new Member();
                member.setRegTime(new Date());
                member.setPhoneNumber(telephone);
                memberService.add(member);
            }
            return new Result(true, MessageConstant.LOGIN_SUCCESS);
        }

        // 验证码输入错误
        return new Result(false, MessageConstant.VALIDATECODE_ERROR);
    }
}
