package com.itheima.controller;

import com.itheima.common.constant.MessageConstant;
import com.itheima.common.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 体检套餐
 */
@RestController
@Slf4j
@RequestMapping("setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;


    /**
     * 获取所有套餐信息
     */
    @PostMapping("/getSetmeal")
    public Result getSetmeal(){
        log.info("获取所有套餐信息");
        try {
            List<Setmeal> setmealList = setmealService.getSetmeal();
            return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS,setmealList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEALLIST_FAIL);
        }
    }

    /**
     * 根据套餐id查询套餐详情
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id){
        log.info("根据套餐id查询套餐详情",id);
        try {
            Setmeal setmeal = setmealService.findById(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}
