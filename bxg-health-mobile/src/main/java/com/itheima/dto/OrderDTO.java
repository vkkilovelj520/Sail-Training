package com.itheima.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private String idCard;// 身份证号
    private String name;// 姓名
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    private Date orderDate;// 预约日期
    private Integer setmealId;// 体检套餐id
    private String sex;// 性别
    private String telephone;// 手机号
    private String validateCode;// 验证码
}
