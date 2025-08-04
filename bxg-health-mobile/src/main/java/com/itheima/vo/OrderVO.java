package com.itheima.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.itheima.pojo.Order;
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

    private String orderType;//预约类型 电话预约/微信预约
    private String member;//姓名
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    private Date orderDate;//预约日期
    private String setmeal;// 套餐名称

}
