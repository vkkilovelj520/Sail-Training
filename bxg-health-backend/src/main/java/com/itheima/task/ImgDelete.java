package com.itheima.task;

import com.itheima.common.constant.RedisConstant;
import com.itheima.common.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Slf4j
public class ImgDelete {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private AliOssUtil aliOssUtil;

    @Scheduled(cron = "0 * * * * ?")
    public void imgDelete() {
        log.info("定时任务开始执行");

        //找差集，然后删除差集里面的照片
        Set<String> diff = redisTemplate.boundSetOps(RedisConstant.SETMEAL_PIC_RESOURCES).diff(RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        for (String s : diff) {
            //删除阿里云上的图片,先获取到图片的名字
            String imgName = s.substring(s.lastIndexOf("/") + 1);
            // 删除图片
            aliOssUtil.delete(imgName);
            //这是将redis中的多余文件名进行删除
            redisTemplate.boundSetOps(RedisConstant.SETMEAL_PIC_RESOURCES).remove(s);
        }
    }
}
