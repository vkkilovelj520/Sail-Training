package com.itheima.service;

import com.itheima.pojo.User;

public interface UserService {
    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    User findByUsername(String username);
}
