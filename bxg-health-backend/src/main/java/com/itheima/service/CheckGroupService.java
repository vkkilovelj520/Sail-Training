package com.itheima.service;

import com.itheima.common.entity.PageResult;
import com.itheima.common.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    PageResult pageQuery(QueryPageBean queryPageBean);

    /**
     * 新增检查组
     * @param checkGroup
     * @param
     */
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    /**
     * 根据id删除检查组
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 根据套餐id查询关联的检查组id
     * @param setmealId
     * @return
     */
    List<Integer> findCheckGroupIdsBySetmealId(Integer setmealId);

    /**
     * 根据id查询检查组
     * @param id
     * @return
     */
    CheckGroup findById(Integer id);

    /**
     * 编辑检查组
     * @param checkGroup
     * @param checkitemIds
     */
    void edit(CheckGroup checkGroup, Integer[] checkitemIds);


    /**
     * 查询所有检查组
     * @return
     */
    List<CheckGroup> findAll();

}
