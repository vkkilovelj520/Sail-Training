package com.itheima.dao;

import com.itheima.pojo.CheckItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserCheckItemDao {

    CheckItem findCheckItemById(Integer id);
}
