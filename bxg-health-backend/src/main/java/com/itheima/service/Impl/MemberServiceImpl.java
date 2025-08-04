package com.itheima.service.Impl;

import com.itheima.common.utils.DateUtils;
import com.itheima.dao.MemberDao;
import com.itheima.service.MemberService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.hutool.core.date.DateUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    /**
     * 获取会员数量统计数据
     * @param list
     * @return
     */
    @SneakyThrows
    public Map<String, Object> findMemberCountByMonths(List<String> list) {
        // 用于存放会员数量
        List<Integer> memberCountList = new ArrayList<>(12);
        // 用于存放日期
        List<String> months = new ArrayList<>(12);
        if (list != null || list.size() != 0) {
            for (String yearMonth : list) {
                // 获取指定月份的最后一天
                String lastDayOfMonth = String.valueOf(DateUtil.getLastDayOfMonth(DateUtils.parseString2Date(yearMonth, "yyyy-MM")));
                // 使用 String.format 内置的两位数格式化
                String fullDate = String.format("%s-%s", yearMonth, lastDayOfMonth);
                months.add(fullDate);// 将月份添加到集合
                Integer memberCount = memberDao.findMemberCountByMouths(fullDate);
                memberCountList.add(memberCount); //将会员数量添加到集合
            }
        }
        // 将数据添加到map
        Map<String, Object> map = new HashMap<>();
        map.put("months", months);
        map.put("memberCount", memberCountList);

        return map;
    }
}
