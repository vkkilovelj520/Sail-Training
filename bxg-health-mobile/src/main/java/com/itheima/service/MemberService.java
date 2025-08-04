package com.itheima.service;

import com.itheima.pojo.Member;

public interface MemberService {

    /**
     * 通过电话号码查询会员信息
     * @param telephone
     * @return
     */
    Member findByTelephone(String telephone);

    /**
     * 新增会员
     * @param member
     */
    void add(Member member);
}
