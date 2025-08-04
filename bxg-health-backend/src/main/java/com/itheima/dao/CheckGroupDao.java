package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CheckGroupDao {

    Page<CheckGroup> pageQuery(String queryString);

    void add(CheckGroup checkGroup);

    void setCheckGroupAndCheckItem(Map<String, Integer> map);

    void deleteCheckItemIdsByCheckGroupId(Integer id);

    void deleteById(Integer id);

    List<Integer> findCheckGroupIdsBySetmealId(Integer setmealId);

    CheckGroup findById(Integer id);

    void edit(CheckGroup checkGroup);

    List<CheckGroup> findAll();
}
