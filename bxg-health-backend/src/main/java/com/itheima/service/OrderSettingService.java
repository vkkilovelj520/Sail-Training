package com.itheima.service;

import com.itheima.pojo.OrderSetting;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    /**
     * 下载模板
     * @param response
     */
    void download(HttpServletResponse response);

    /**
     * 根据日期修改可预约人数
     * @param orderSetting
     */
    void editNumberByOrderDate(OrderSetting orderSetting);

    /**
     * 根据年月查询对应的预约设置信息
     * @param month
     * @return
     */
    List<Map> getOrderSettingByMonth(String month);

    /**
     * 文件上传，实现预约设置数据批量导入
     * @param list
     */
    void add(List<OrderSetting> list);
}
