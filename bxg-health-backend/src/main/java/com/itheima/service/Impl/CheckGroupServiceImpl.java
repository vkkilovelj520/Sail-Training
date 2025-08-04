package com.itheima.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.common.entity.PageResult;
import com.itheima.common.entity.QueryPageBean;
import com.itheima.dao.CheckGroupDao;
import com.itheima.dao.CheckItemDao;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class  CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;
    @Autowired
    private CheckItemDao checkItemDao;

    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return
     */
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        String queryString = queryPageBean.getQueryString();
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();

        PageHelper.startPage(currentPage, pageSize);
        Page<CheckGroup> page = checkGroupDao.pageQuery(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 新增检查组
     *
     * @param checkGroup
     * @param checkitemIds
     */
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        // 新增检查组，操作t_checkGroup表
        checkGroupDao.add(checkGroup);
        // 设置检查项和检查组的多对多关系
        Integer checkGroupId = checkGroup.getId();
        if (checkitemIds != null && checkitemIds.length > 0) {
            for (Integer checkitemId : checkitemIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("checkgroupId", checkGroupId);
                map.put("checkitemId", checkitemId);
                checkGroupDao.setCheckGroupAndCheckItem(map);
            }
        }
    }

    /**
     * 根据id删除检查组
     * @param id
     */
    public void deleteById(Integer id) {
        // 先删除关联表的检查项
        checkGroupDao.deleteCheckItemIdsByCheckGroupId(id);
        // 删除检查组
        checkGroupDao.deleteById(id);
    }

    /**
     * 根据套餐id查询关联的检查组id
     * @param setmealId
     * @return
     */
    public List<Integer> findCheckGroupIdsBySetmealId(Integer setmealId) {
        List<Integer> checkgroupIds =  checkGroupDao.findCheckGroupIdsBySetmealId(setmealId);
        return checkgroupIds;
    }

    /**
     * 根据id查询检查组
     * @param id
     * @return
     */
    public CheckGroup findById(Integer id) {
        CheckGroup checkGroup = checkGroupDao.findById(id);
        return checkGroup;
    }


    /**
     * 编辑检查组
     * @param checkGroup
     * @param checkitemIds
     */
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        // 编辑检查组
        checkGroupDao.edit(checkGroup);
        // 设置检查项和检查组的多对多关系操作t_checkGroup_checkItem表
        // 获取checkGroupId，先删除之前checkItemIds的数据
        Integer checkgroupId = checkGroup.getId();
        checkGroupDao.deleteCheckItemIdsByCheckGroupId(checkgroupId);
        if (checkitemIds != null && checkitemIds.length > 0){
            for (Integer checkitemId : checkitemIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("checkitemId", checkitemId);
                map.put("checkgroupId", checkgroupId);
                checkGroupDao.setCheckGroupAndCheckItem(map);
            }
        }

    }

    /**
     * 查询所有检查组
     * @return
     */
    public List<CheckGroup> findAll() {
        List<CheckGroup> checkGroupList = checkGroupDao.findAll();
        return checkGroupList;
    }


}
