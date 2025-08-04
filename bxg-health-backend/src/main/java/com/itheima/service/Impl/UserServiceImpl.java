package com.itheima.service.Impl;

import com.itheima.dao.PermissionDao;
import com.itheima.dao.RoleDao;
import com.itheima.dao.UserDao;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;


    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    public User findByUsername(String username) {
        User user = userDao.findByUsername(username);//查询用户的基本信息，不包含用户的角色
        if (user == null){
            return null;
        }
        Integer userId = user.getId();
        //根据用户id查询对应的角色
        Set<Role> roles = roleDao.findByUserId(userId); //根据用户id查询角色
        for (Role role : roles) {
            Integer roleId = role.getId();
            //根据角色id查询关联的权限
            Set<Permission> permissions = permissionDao.findByRoleId(roleId); //根据角色id查询关联的权限
            role.setPermissions(permissions);
        }
        user.setRoles(roles);
        return user;
    }
}
