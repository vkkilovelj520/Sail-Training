package com.itheima.service;

import com.itheima.common.entity.Result;
import com.itheima.dto.OrderDTO;
import com.itheima.vo.OrderVO;

public interface OrderService {

    /**
     * 根据预约id查询预约信息
     * @param id
     * @return
     */
    OrderVO findById(Integer id);

    /**
     * 体检预约
     * @param orderDTO
     * @return
     */
    Result submitOrder(OrderDTO orderDTO);
}
