package com.itheima.dao;

import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    /**
     * 根据用户姓名查询用户信息
     * @param username
     * @return
     */
    User findByUsername(String username);
}
