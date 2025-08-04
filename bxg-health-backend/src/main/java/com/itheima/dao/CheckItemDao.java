package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CheckItemDao {

    /**
     * 新增检查项
     *
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 根据id删除检查项
     *
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 根据id查询检查项
     *
     * @param id
     * @return
     */
    @Select("select * from health.t_checkitem where id = #{id}")
    CheckItem getById(Integer id);

    /**
     * 编辑检查项
     *
     * @param checkItem
     */
    void edit(CheckItem checkItem);


    /**
     * 检查项分页查询
     *
     * @param queryString
     * @return
     */
    Page<CheckItem> pageQuery(String queryString);

    /**
     * 查询所有检查项
     *
     * @return
     */
    List<CheckItem> findAll();

    /**
     * 根据检查组id查询检查组包含的检查项id
     *
     * @param checkgroupId
     * @return
     */
    List<Integer> findCheckItemIdsByCheckGroupId(Integer checkgroupId);
}

