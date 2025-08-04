package com.itheima.service;

import com.itheima.common.entity.PageResult;
import com.itheima.common.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {
    /**
     * 新增检查项
     * @param checkItem
     */
    void addCheckItem(CheckItem checkItem);

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    PageResult pageQuery(QueryPageBean queryPageBean);

    /**
     * 根据id删除检查项
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 编辑检查项
     * @param checkItem
     */
    void edit(CheckItem checkItem);

    /**
     * 根据id查询检查项
     * @return
     * @param id
     */
    CheckItem findById(Integer id);

    /**
     * 查询所有检查项
     * @return
     */
    List<CheckItem> findAll();

    /**
     * 根据检查组id查询检查组包含的检查项id
     * @param checkgroupId
     * @return
     */
    List<Integer> findCheckItemIdsByCheckGroupId(Integer checkgroupId);
}
