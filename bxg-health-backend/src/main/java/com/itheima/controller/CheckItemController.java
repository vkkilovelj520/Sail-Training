package com.itheima.controller;

import com.itheima.common.constant.MessageConstant;
import com.itheima.common.entity.PageResult;
import com.itheima.common.entity.QueryPageBean;
import com.itheima.common.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 检查项管理
 */
@RestController
@Slf4j
@RequestMapping("checkitem")
public class CheckItemController {

    @Autowired
    private CheckItemService checkItemService;

    /**
     * 新增
     */
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('CHECKITEM_ADD')")
    public Result add(@RequestBody CheckItem checkItem){
        log.info("新增检查项：{}", checkItem);
        try {
            checkItemService.addCheckItem(checkItem);
            // 调用成功
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            // 调用失败
            return new Result(false,MessageConstant.ADD_CHECKITEM_FAIL);
        }
    }


    /**
     * 分页查询
     */
    @PostMapping("findPage")
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    public PageResult page(@RequestBody QueryPageBean queryPageBean){
        log.info("检查项分页查询：{}",queryPageBean.toString());

        PageResult pageResult = checkItemService.pageQuery(queryPageBean);
        return pageResult;
    }


    /**
     * 删除
     */
    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")
    public Result delete(@RequestParam Integer id){
        log.info("删除检查项：{}", id);

        try {
            checkItemService.deleteById(id);
            // 删除成功
            return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            // 删除失败
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
    }

    /**
     * 修改
     */
    @PreAuthorize("hasAuthority('CHECKITEM_EDIT')")
    @PostMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem){
        log.info("修改检查项：{}", checkItem.toString());

        try {
            checkItemService.edit(checkItem);
            return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKITEM_SUCCESS);
        }
    }

    /**
     * 查询
     */
    @GetMapping("/findById")
    public Result findById(@RequestParam Integer id) {
        log.info("根据id查询检查项：{}", id);

        try {
            CheckItem checkItem = checkItemService.findById(id);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    /**
     * 查询所有检查项
     */
    @GetMapping("/findAll")
    public Result findAll(){

        try {
            List<CheckItem> checkItems = checkItemService.findAll();
            log.info("查询所有检查项：{}", checkItems);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItems);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }


    /**
     * 根据检查组id查询检查组包含的检查项id
     * @return
     */
    @GetMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(@RequestParam Integer checkgroupId){
        log.info("根据检查组id查询检查项id:{}", checkgroupId);
        try {
            List<Integer> checkItemIds = checkItemService.findCheckItemIdsByCheckGroupId(checkgroupId);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItemIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }
}
