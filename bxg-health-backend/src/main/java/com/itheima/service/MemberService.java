package com.itheima.service;

import java.util.List;
import java.util.Map;

public interface MemberService {


    /**
     * 获取会员数量统计数据
     * @param months
     * @return
     */
    Map<String, Object> findMemberCountByMonths(List<String> months);
}
