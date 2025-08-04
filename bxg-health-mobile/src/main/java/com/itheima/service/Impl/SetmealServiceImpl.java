package com.itheima.service.Impl;

import com.itheima.dao.UserSetmealDao;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private UserSetmealDao setmealDao;


    /**
     * 获取所有套餐信息
     * @return
     */
    public List<Setmeal> getSetmeal() {
        List<Setmeal> setmealList = setmealDao.findAll();
        return setmealList;
    }

    /**
     * 根据套餐id查询套餐详情
     * @param id
     * @return
     */
    public Setmeal findById(Integer id) {

        return setmealDao.findById(id);
    }
}
