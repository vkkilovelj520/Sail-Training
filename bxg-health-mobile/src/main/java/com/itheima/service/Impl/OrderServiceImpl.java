package com.itheima.service.Impl;

import com.itheima.common.constant.MessageConstant;
import com.itheima.common.entity.Result;
import com.itheima.common.utils.DateUtils;
import com.itheima.dao.UserMemberDao;
import com.itheima.dao.UserOrderDao;
import com.itheima.dao.UserOrderSettingDao;
import com.itheima.dto.OrderDTO;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.vo.OrderVO;
import com.itheima.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{

    @Autowired
    private UserOrderDao orderDao;
    @Autowired
    private UserOrderSettingDao orderSettingDao;
    @Autowired
    private UserMemberDao memberDao;

    /**
     * 根据预约id查询预约信息
     * @param id
     * @return
     */

    public OrderVO findById(Integer id) {
        OrderVO orderVO = orderDao.findById(id);
        return orderVO;
    }

    /**
     * 体检预约
     * @param orderDTO
     * @return
     */
    public Result submitOrder(OrderDTO orderDTO) {
        //1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(orderDTO.getOrderDate());
        if (orderSetting == null){
            //指定日期没有进行预约设置，无法完成体检预约
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        //2、检查所选日期是否已经约满，如果约满，则无法预约
        if (orderSetting.getReservations() >= orderSetting.getNumber()){
            // 已经约满，无法预约
            return new Result(false, MessageConstant.ORDER_FULL);
        }
        //3、检查用户是否重复预约（同一个用户在同一天只能预约同一个套餐）,如果重复预约，则无法再次预约
        // 判断是否注册会员
        Member member = memberDao.findByTelephone(orderDTO.getTelephone());
        if (member != null){
            //判断是否重复预约
            Order order = new Order(member.getId(), orderDTO.getOrderDate(), orderDTO.getSetmealId());
            // 根据条件查询
            List<Order> orders = orderDao.findByOrder(order);
            if (orders != null && orders.size() > 0){
                // 说明用户在重复预约
                return new Result(false, MessageConstant.HAS_ORDERED);
            }
        }else {
            // 到这说明用户不是会员，自动注册会员并进行预约
            new Member();
           member = new Member();
           member.setName(orderDTO.getName());
           member.setSex(orderDTO.getSex());
           member.setIdCard(orderDTO.getIdCard());
           member.setPhoneNumber(orderDTO.getTelephone());
           member.setRegTime(new Date());
           memberDao.add(member); // 自动完成会员注册

        }

        //5、预约成功，更新当日的已预约人数
        Order order = new Order(member.getId(),orderDTO.getOrderDate(),Order.ORDERTYPE_WEIXIN, Order.ORDERSTATUS_NO, orderDTO.getSetmealId());
        orderDao.add(order);

        // 设置已预约人数 +1
        orderSetting.setReservations(orderSetting.getReservations() + 1);
        orderSettingDao.editReservationsByOrderDate(orderSetting);
        return new Result(true, MessageConstant.ORDER_SUCCESS, order.getId());
    }
}
