package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Order;
import com.itheima.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderDao {


    Page<OrderVO> pageQuery(String queryString);

    @Select("select * from t_order where id = #{id}")
    Order getById(Integer id);

    void edit(Order orderDate);

    Integer findOrderCountByDate(String today);

    Integer findOrderCountAfterDate(String thisWeekMonday);

    Integer findVisitsCountByDate(String today);

    Integer findVisitsCountAfterDate(String thisWeekMonday);

    List<Map> findHotSetmeal();

}
