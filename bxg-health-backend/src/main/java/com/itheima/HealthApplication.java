package com.itheima;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;



@SpringBootApplication
@EnableScheduling
@MapperScan("com.itheima.dao")
public class HealthApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthApplication.class, args);
    }


    /**
     * 项目启动输出访问地址
     */
    @Bean
    public void start(){
        System.out.println("管理端地址：http://localhost:8080/backend/pages/main.html");
    }

}
