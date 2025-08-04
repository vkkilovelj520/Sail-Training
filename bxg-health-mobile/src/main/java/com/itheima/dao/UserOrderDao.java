package com.itheima.dao;

import com.itheima.pojo.Order;
import com.itheima.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserOrderDao {

    OrderVO findById(Integer id);

    List<Order> findByOrder(Order order);

    void add(Order order);
}
