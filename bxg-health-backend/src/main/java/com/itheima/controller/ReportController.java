package com.itheima.controller;

import com.itheima.common.constant.MessageConstant;
import com.itheima.common.entity.Result;
import com.itheima.common.utils.DateUtils;
import com.itheima.service.MemberService;
import com.itheima.service.ReportService;
import com.itheima.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/report")
@Slf4j
public class ReportController{

    @Autowired
    private MemberService memberService;
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private ReportService reportService;

    /**
     * 获取会员数量统计数据
     * @return
     */
    @GetMapping("/getMemberReport")
    @PreAuthorize("hasAuthority('REPORT_VIEW')")
    public Result getMemberReport() throws Exception {
        log.info("获取会员数量统计数据");
        try {
            // 获取日历对象
            Calendar calendar = Calendar.getInstance();
            // 根据当前日期，获取前12个月的日历（当前日历，2024-7，12个月前，日历时间2023-8）
            calendar.add(Calendar.MONTH, -12);
            List<String> months = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                calendar.add(Calendar.MONTH, 1);
                months.add(new SimpleDateFormat("yyyy-MM").format(calendar.getTime()));
            }
            // 查询所有的会员
            Map<String, Object> map = memberService.findMemberCountByMonths(months);

            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }

    }

    /**
     * 获取套餐占比统计数据
     */
    @GetMapping("/getSetmealReport")
    @PreAuthorize("hasAuthority('REPORT_VIEW')")
    public Result getSetmealReport(){
        log.info("获取套餐占比统计数据");

        try {
            List<Map<String, Object>> setmealCount = setmealService.findSetmealCount();
            List<String> setmealNames = new ArrayList<>();
            for (Map<String, Object> map : setmealCount) {
                String name = (String) map.get("name");
                setmealNames.add(name);
            }
            Map<String, Object> data = new HashMap<>();
            data.put("setmealNames", setmealNames);
            data.put("setmealCount", setmealCount);

            return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS, data);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
    }

    /**
     * 运营数据统计
     * @return
     */
    @GetMapping("/getBusinessReportData")
    @PreAuthorize("hasAuthority('REPORT_VIEW')")
    public Result getBusinessReportData(){
        try {
            Map<String,Object> data = reportService.getBusinessReportData();
            return new Result(true,MessageConstant.GET_BUSINESS_REPORT_SUCCESS,data);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    /**
     * 导出Excel
     * @return
     */
    @GetMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletResponse response){
        try {
            Map<String,Object> result = reportService.getBusinessReportData();

            //取出返回结果，准备将报表数据写入到Excel文件
            String reportDate = (String) result.get("reportDate");
            Integer todayNewMember = (Integer) result.get("todayNewMember");
            Integer totalMember = (Integer) result.get("totalMember");
            Integer thisWeekNewMember = (Integer) result.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) result.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) result.get("todayOrderNumber");
            Integer thisWeekOrderNumber = (Integer) result.get("thisWeekOrderNumber");
            Integer thisMonthOrderNumber = (Integer) result.get("thisMonthOrderNumber");
            Integer todayVisitsNumber = (Integer) result.get("todayVisitsNumber");
            Integer thisWeekVisitsNumber = (Integer) result.get("thisWeekVisitsNumber");
            Integer thisMonthVisitsNumber = (Integer) result.get("thisMonthVisitsNumber");
            List<Map> hotSetmeal = (List<Map>) result.get("hotSetmeal");

            // 获得excel模板文件绝对路径

            InputStream inputStream = response.getClass().getClassLoader().getResourceAsStream("templates" + File.separator + "report_template.xlsx");
            //基于提供的Excel模板文件在内存中创建一个excel表格对象
            XSSFWorkbook excel = new XSSFWorkbook(inputStream);
            // 读取第一个工作表
            XSSFSheet sheet = excel.getSheetAt(0);
            // 获得第三行
            XSSFRow row = sheet.getRow(2);
            row.getCell(5).setCellValue(reportDate);//报表日期

            row = sheet.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);//新增会员数（本日）
            row.getCell(7).setCellValue(totalMember);//总会员数

            row = sheet.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);//本周新增会员数
            row.getCell(7).setCellValue(thisMonthNewMember);//本月新增会员数

            row = sheet.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
            row.getCell(7).setCellValue(todayVisitsNumber);//今日到诊数

            row = sheet.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
            row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周到诊数

            row = sheet.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
            row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月到诊数

            int rowNum = 12;
            for (Map map : hotSetmeal) {
                String name = (String) map.get("name");
                Long setmeal_count = (Long) map.get("setmeal_count");
                BigDecimal proportion = (BigDecimal) map.get("proportion");
                row = sheet.getRow(rowNum++);
                row.getCell(4).setCellValue(name);//套餐名称
                row.getCell(5).setCellValue(setmeal_count);//预约数量
                row.getCell(6).setCellValue(proportion.doubleValue());//占比
            }

            //通过输出流进行文件下载
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
            excel.write(outputStream);

            outputStream.flush();
            outputStream.close();
            excel.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

}
