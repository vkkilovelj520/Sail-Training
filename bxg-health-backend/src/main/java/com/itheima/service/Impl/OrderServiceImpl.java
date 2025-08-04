package com.itheima.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.common.entity.PageResult;
import com.itheima.common.entity.QueryPageBean;
import com.itheima.common.entity.Result;
import com.itheima.dao.OrderDao;
import com.itheima.pojo.Order;
import com.itheima.service.OrderService;
import com.itheima.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.itheima.pojo.Order.*;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        String queryString = queryPageBean.getQueryString();
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();

        PageHelper.startPage(currentPage,pageSize);
        Page<OrderVO> page = orderDao.pageQuery(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 修改状态
     * @param order
     * @return
     */
    public Result update(Order order) {

        // 先获取到id
        Order od = orderDao.getById(order.getId());
        String orderStatus = od.getOrderStatus();
        if (orderStatus.equals(ORDERSTATUS_NO)){
            // 未到诊
            orderDao.edit(order);
        }else if (orderStatus.equals(ORDERSTATUS_CANCEL)){
            // 已取消
            return new Result(false, "该用户已取消");
        }else if (orderStatus.equals(ORDERSTATUS_YES)){
            // 已到诊
            return new Result(false, "该用户已到诊");
        }
        return new Result(true, "修改成功");
    }
}
