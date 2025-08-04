package com.itheima.service.Impl;

import com.itheima.common.utils.DateUtils;
import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


@Service
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    /**
     * 模板下载
     * @param response
     */
    public void download(HttpServletResponse response) {
        //导出表名称，但Postman统一为response,网页上下载会以此命名
        InputStream inputStream = response.getClass().getClassLoader().getResourceAsStream("templates" + File.separator + "ordersetting_template.xlsx");
        try {
            //设置类型
            response.setContentType("application/vnd.ms-excel");
            //导出表名称，但Postman统一为response,网页上下载会以此命名
            response.setHeader("content-Disposition", "attachment;filename = ordersetting_template.xlsx");

            //通过输入流读取指定的Excel文件
            XSSFWorkbook excel = new XSSFWorkbook(inputStream);
            ServletOutputStream outputStream = response.getOutputStream();
            excel.write(outputStream);
            outputStream.close();
            excel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件上传，实现预约设置数据批量导入
     * @param list
     */
    public void add(List<OrderSetting> list) {
        if (list != null && list.size() > 0){
            for (OrderSetting orderSetting : list) {
                // 判断当前日期是否进行了预约设置
                Integer countByOrderDate = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
                if (countByOrderDate > 0){
                    // 已经进行了预约设置，执行更新操作
                    orderSettingDao.updateNumberByOrderDate(orderSetting);
                }else {
                    // 没有进行更新设置，执行新增操作
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }


    /**
     * 根据日期修改可预约人数
     * @param orderSetting
     */
    public void editNumberByOrderDate(OrderSetting orderSetting) {
        Date orderDate = orderSetting.getOrderDate();
        try {
            // 将日期格式化为String类型
            String dataSting = DateUtils.parseDate2String(orderDate);
            // 根据日期获取id
            Integer id = orderSettingDao.editIdByOrderDate(dataSting);
            //再根据id去修改预约日期
            orderSettingDao.editNumberById(id,orderSetting.getNumber());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据年月查询对应的预约设置信息
     * @param data
     * @return
     */
    public List<Map> getOrderSettingByMonth(String data) {
        // 2024-7
        String[] split = data.split("-");
        String year = split[0];
        String month = split[1];
        Map<String, String> map = new HashMap<>();
        map.put("year", year);
        map.put("month", month);
        // 根据年月查询对应的预约设置信息
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(map);
        List<Map> result = new ArrayList<>();
        if (list != null && list.size() > 0){
            for (OrderSetting orderSetting : list) {
                Map<String,Object> m = new HashMap();
                m.put("date", orderSetting.getOrderDate().getDate());
                m.put("number", orderSetting.getNumber());
                m.put("reservations", orderSetting.getReservations());
                result.add(m);
            }
        }
        return result;
    }

}
