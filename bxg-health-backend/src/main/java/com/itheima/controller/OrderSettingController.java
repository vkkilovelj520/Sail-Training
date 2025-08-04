package com.itheima.controller;

import com.itheima.common.constant.MessageConstant;
import com.itheima.common.entity.Result;
import com.itheima.common.utils.POIUtils;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 预约设置
 */
@RestController
@RequestMapping("/ordersetting")
@Slf4j
public class OrderSettingController {

    @Autowired
    private OrderSettingService orderSettingService;

    /**
     * 模板下载
     * @param response
     */
    @GetMapping("/download")
    public void export(HttpServletResponse response){
        log.info("下载模板：{}",response);
        orderSettingService.download(response);
    }

    /**
     * 文件上传，实现预约设置数据批量导入
     * @param excelFile
     * @return
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile excelFile){
        log.info("文件上传：{}", excelFile);
        try {
            // 通过工具类，从Excel文件中读取数据到字符串数组列表
            List<String[]> list = POIUtils.readExcel(excelFile);
            List<OrderSetting> data = new ArrayList<>();
            // 遍历读取到的数据，构建OrderSetting对象，并添加到数据列表中
            for (String[] strings : list) {
                String orderData = strings[0];
                String number = strings[1];
                OrderSetting orderSetting = new OrderSetting(new Date(orderData), Integer.parseInt(number));
                data.add(orderSetting);
            }
            orderSettingService.add(data);
            return new Result(true,MessageConstant.IMPORT_ORDERSETTING_SUCCESS);//上传成功

        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);//上传失败
        }
    }

    /**
     * 根据日期修改可预约人数
     * @param orderSetting
     * @return
     */
    @PostMapping("/editNumberByOrderDate")
    @PreAuthorize("hasAuthority('ORDERSETTING')")
    public Result editNumberByOrderDate(@RequestBody OrderSetting orderSetting){
        log.info("根据日期修改可预约人数");
        try {
            orderSettingService.editNumberByOrderDate(orderSetting);
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }

    /**
     * 根据年月查询对应的预约设置信息
     * @param month
     * @return
     */
    @GetMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String month){
        log.info("根据年月查询对应的预约设置信息:{}",month);
        try {
            List<Map> list = orderSettingService.getOrderSettingByMonth(month);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }
}
