package com.itheima.dao;

import com.itheima.pojo.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMemberDao {

    Member findByTelephone(String telephone);

    void add(Member member);
}
