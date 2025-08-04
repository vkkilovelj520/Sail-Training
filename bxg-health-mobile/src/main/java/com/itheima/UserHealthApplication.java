package com.itheima;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.itheima.dao")
public class UserHealthApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserHealthApplication.class, args);
    }

    /**
     * 项目启动输出访问地址
     */
    @Bean
    public void start(){
        System.out.println("移动端地址：http://localhost/mobile/pages/index.html");
    }

}
