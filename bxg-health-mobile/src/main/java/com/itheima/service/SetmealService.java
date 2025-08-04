package com.itheima.service;

import com.itheima.pojo.Setmeal;

import java.util.List;

public interface SetmealService {

    /**
     * 获取所有套餐信息
     * @return
     */
    List<Setmeal> getSetmeal();

    /**
     * 根据套餐id查询套餐详情
     * @param id
     * @return
     */
    Setmeal findById(Integer id);
}
