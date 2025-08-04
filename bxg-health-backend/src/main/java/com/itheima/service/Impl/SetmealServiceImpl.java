package com.itheima.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.common.entity.PageResult;
import com.itheima.common.entity.QueryPageBean;
import com.itheima.dao.SetmealDao;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;


    /**
     * 新增体检套餐
     * @param setmeal
     * @param checkgroupIds
     */
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        // 新增套餐表
        setmealDao.add(setmeal);
        // 获取到当前套餐id
        Integer setmealId = setmeal.getId();
        // 将套餐id和检查组id关联
        CheckgroupIdsAndSetmealId(checkgroupIds, setmealId);
    }

    /**
     * 套餐分页查询
     * @param queryPageBean
     * @return
     */
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer pageSize = queryPageBean.getPageSize();
        Integer currentPage = queryPageBean.getCurrentPage();
        String queryString = queryPageBean.getQueryString();

        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page = setmealDao.pageQuery(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    public Setmeal findById(Integer id) {
        Setmeal setmeal = setmealDao.findById(id);
        return setmeal;
    }

    /**
     * 编辑套餐
     * @param setmeal
     * @param checkGroupIds
     */
    public void edit(Setmeal setmeal, Integer[] checkGroupIds) {
        // 先编辑套餐表
        setmealDao.edit(setmeal);
        // 获取到当前套餐id
        Integer setmealId = setmeal.getId();
        setmealDao.deleteCheckGroupIdsBySetmealId(setmealId);
        // 将套餐id和检查组id关联
        CheckgroupIdsAndSetmealId(checkGroupIds,setmealId);
    }

    /**
     *获取套餐占比统计数据
     * @return
     */
    public List<Map<String, Object>> findSetmealCount() {

        return setmealDao.findSetmealCount();

    }


    /**
     * 将套餐和检查组关联
     * @param checkgroupIds
     * @param setmealId
     */
    private void CheckgroupIdsAndSetmealId(Integer[] checkgroupIds, Integer setmealId) {
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            for (Integer checkgroupId : checkgroupIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("checkgroupId", checkgroupId);
                map.put("setmealId", setmealId);
                setmealDao.setSetmealAndCheckGroup(map);
            }
        }
    }
}
