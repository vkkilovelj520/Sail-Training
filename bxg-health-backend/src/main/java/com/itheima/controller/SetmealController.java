package com.itheima.controller;

import com.itheima.common.constant.MessageConstant;
import com.itheima.common.constant.RedisConstant;
import com.itheima.common.entity.PageResult;
import com.itheima.common.entity.QueryPageBean;
import com.itheima.common.entity.Result;
import com.itheima.common.utils.AliOssUtil;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 体检套餐管理
 */
@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private AliOssUtil aliOssUtil;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 新增体检套餐
     *
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('SETMEAL_ADD')")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        log.info("新增体检套餐");
        try {
            setmealService.add(setmeal, checkgroupIds);
            // 将图片缓存到redis,用于解决垃圾图片处理问题
            redisTemplate.boundSetOps(RedisConstant.SETMEAL_PIC_DB_RESOURCES).add(setmeal.getImg());
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    /**
     * 套餐分页查询
     *
     * @param queryPageBean
     * @return
     */
    @PostMapping("/findPage")
    @PreAuthorize("hasAuthority('SETMEAL_QUERY')")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        log.info("体检套餐分页查询：{}", queryPageBean);
        PageResult pageResult = setmealService.pageQuery(queryPageBean);
        return pageResult;
    }

    /**
     * 根据id查询套餐
     *
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(Integer id) {
        log.info("根据id查询套餐：{}", id);
        try {
            Setmeal setmeal = setmealService.findById(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

    /**
     * 编辑套餐
     *
     * @param setmeal
     * @param checkGroupIds
     * @return
     */
    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('SETMEAL_EDIT')")
    public Result edit(@RequestBody Setmeal setmeal, Integer[] checkGroupIds) {
        log.info("编辑套餐");
        try {
            setmealService.edit(setmeal, checkGroupIds);
            // 将图片缓存到redis,用于解决垃圾图片处理问题
            redisTemplate.boundSetOps(RedisConstant.SETMEAL_PIC_DB_RESOURCES).add(setmeal.getImg());
            return new Result(true, MessageConstant.EDIT_SETMEAL_SUCCESS);//编辑成功
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_SETMEAL_FAIL);//编辑失败
        }
    }

    /**
     * 文件上传
     *
     * @param imgFile
     * @return
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile imgFile) throws IOException {
        log.info("文件上传：{}", imgFile);

        try {
            // 先获取原始文件名
            String originalFilename = imgFile.getOriginalFilename();
            // 截取原始文件名后缀
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 构造新的文件名
            String newObjectName = UUID.randomUUID().toString() + extension;
            // 文件的请求路径
            String filePath = aliOssUtil.upload(imgFile.getBytes(), newObjectName);
            // 将图片缓存到redis,用于解决垃圾图片处理问题
            redisTemplate.boundSetOps(RedisConstant.SETMEAL_PIC_RESOURCES).add(filePath);

            return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }
}
