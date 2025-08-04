package com.itheima.dao;

import com.itheima.pojo.CheckGroup;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserCheckGroupDao {

    CheckGroup findCheckGroupById(Integer id);
}
