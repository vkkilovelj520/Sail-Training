package com.itheima.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDao {

    Integer findMemberCountByMouths(String lastDayOfMonth);

    Integer findMemberTotalCount();

    Integer findMemberCountByDate(String today);

    Integer findMemberCountAfterDate(String thisWeekMonday);
}
