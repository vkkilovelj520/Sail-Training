package com.itheima.dao;

import com.itheima.pojo.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

@Mapper
public interface PermissionDao {
    /**
     * 根据角色id查询关联的权限
     */
    Set<Permission> findByRoleId(Integer roleId);
}
