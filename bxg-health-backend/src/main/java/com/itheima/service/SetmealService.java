package com.itheima.service;

import com.itheima.common.entity.PageResult;
import com.itheima.common.entity.QueryPageBean;
import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealService {
    /**
     * 新增体检套餐
     * @param setmeal
     * @param checkgroupIds
     */
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 套餐分页查询
     * @param queryPageBean
     * @return
     */
    PageResult pageQuery(QueryPageBean queryPageBean);

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    Setmeal findById(Integer id);

    /**
     * 编辑套餐
     * @param setmeal
     * @param checkGroupIds
     */
    void edit(Setmeal setmeal, Integer[] checkGroupIds);

    /**
     *获取套餐占比统计数据
     * @return
     */
    List<Map<String, Object>> findSetmealCount();

}
