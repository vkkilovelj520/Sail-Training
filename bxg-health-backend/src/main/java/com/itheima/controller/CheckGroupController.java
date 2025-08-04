package com.itheima.controller;

import com.itheima.common.constant.MessageConstant;
import com.itheima.common.entity.PageResult;
import com.itheima.common.entity.QueryPageBean;
import com.itheima.common.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 检查组管理
 */
@RestController
@Slf4j
@RequestMapping("checkgroup")
public class CheckGroupController {

    @Autowired
    private CheckGroupService checkGroupService;

    /**
     * 分页查询
     */
    @PostMapping("findPage")
    @PreAuthorize("hasAuthority('CHECKGROUP_QUERY')")
    public PageResult page(@RequestBody QueryPageBean queryPageBean) {
        log.info("检查组分页查询：{}", queryPageBean.toString());

        PageResult pageResult = checkGroupService.pageQuery(queryPageBean);
        return pageResult;
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('CHECKGROUP_ADD')")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        log.info("新增检查组");
        try {
            checkGroupService.add(checkGroup, checkitemIds);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }

    /**
     * 删除
     */
    @GetMapping("deleteById")
    @PreAuthorize("hasAuthority('CHECKGROUP_DELETE')")
    public Result deleteById(@RequestParam Integer id){
        log.info("删除检查组：{}", id);
        try {
            checkGroupService.deleteById(id);
            return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL);
        }

    }


    /**
     * 根据套餐id查询关联的检查组id
     */
    @GetMapping("/findCheckGroupIdsBySetmealId")
    public Result findCheckGroupIdsBySetmealId(@RequestParam Integer setmealId){
        log.info("根据套餐id查询关联的检查组id:{}", setmealId);

        try {
            List<Integer> checkgroupIds = checkGroupService.findCheckGroupIdsBySetmealId(setmealId);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkgroupIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }


    /**
     * 根据id查询检查组
     */
    @GetMapping("/findById")
    public Result findById(@RequestParam Integer id){
        log.info("根据id查询检查组：{}", id);
        try {
            CheckGroup checkGroup = checkGroupService.findById(id);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }


    /**
     * 编辑检查组
     */
    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('CHECKGROUP_EDIT')")
    public Result edit(@RequestBody CheckGroup checkGroup, @RequestParam Integer[] checkitemIds){
        log.info("编辑检查组");
        try {
            checkGroupService.edit(checkGroup, checkitemIds);
            return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }

    /**
     * 查询所有检查组
     */
    @GetMapping("/findAll")
    public Result findAll(){
        try {
            List<CheckGroup> checkGroups = checkGroupService.findAll();
            log.info("查询所有检查组：{}", checkGroups);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroups);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }
}
