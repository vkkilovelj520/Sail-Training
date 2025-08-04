package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Setmeal;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SetmealDao {
    void add(Setmeal setmeal);

    void setSetmealAndCheckGroup(Map<String, Integer> map);

    Page<Setmeal> pageQuery(String queryString);

    Setmeal findById(Integer id);

    void edit(Setmeal setmeal);

    void deleteCheckGroupIdsBySetmealId(Integer setmealId);

    List<Map<String, Object>> findSetmealCount();

}
