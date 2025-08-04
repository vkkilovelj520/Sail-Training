package com.itheima.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.common.entity.PageResult;
import com.itheima.common.entity.QueryPageBean;
import com.itheima.dao.CheckItemDao;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;


    /**
     * 新增检查项
     * @param checkItem
     */
    public void addCheckItem(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }


    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer pageSize = queryPageBean.getPageSize();
        Integer currentPage = queryPageBean.getCurrentPage();
        String queryString = queryPageBean.getQueryString();

        // 基于mybatis的分页插件PageHelper
        PageHelper.startPage(currentPage, pageSize);
        Page<CheckItem> page = checkItemDao.pageQuery(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据id删除检查项
     * @param id
     */
    public void deleteById(Integer id) {
        // 根据id查询检查项
        CheckItem checkItem = checkItemDao.getById(id);
        // 判断检查项是否存在
        if (checkItem != null){
            // 存在删除检查项
            checkItemDao.deleteById(id);
        }

    }

    /**
     * 编辑检查项
     * @param checkItem
     */
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }

    /**
     * 根据id查询检查项
     * @return
     * @param id
     */
    public CheckItem findById(Integer id) {
        CheckItem checkItem = checkItemDao.getById(id);
        return checkItem;
    }


    /**
     * 查询所有检查项
     * @return
     */
    public List<CheckItem> findAll() {
        List<CheckItem> checkItems = checkItemDao.findAll();
        return checkItems;
    }

    /**
     * 根据检查组id查询检查组包含的检查项id
     * @param checkgroupId
     * @return
     */
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer checkgroupId) {
        List<Integer> checkItemIds = checkItemDao.findCheckItemIdsByCheckGroupId(checkgroupId);
        return checkItemIds;
    }
}
