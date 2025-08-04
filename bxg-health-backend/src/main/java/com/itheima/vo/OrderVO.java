package com.itheima.vo;

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
public class OrderVO{
    private Integer id;//id
    private String fileNumber;//档案号
    private String name;//姓名
    private String phoneNumber;//手机号
    private String orderType;//预约类型 电话预约/微信预约
    private String orderStatus;//预约状态（是否到诊）
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    private Date orderDate;//预约日期
}
