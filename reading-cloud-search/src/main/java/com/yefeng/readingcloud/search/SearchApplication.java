package com.yefeng.readingcloud.search;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableFeignClients(basePackages = {"com.yefeng.readingcloud.book.feign", "com.yefeng.readingcloud.account.feign"})
@SpringBootApplication(scanBasePackages = {"com.yefeng.readingcloud.search", "com.yefeng.readingcloud.common", "com.yefeng.readingcloud.book.feign", "com.yefeng.readingcloud.account.feign"})
//@SpringBootApplication
@MapperScan({"com.yefeng.readingcloud.search.dao","com.baomidou.mybatisplus.samples.quickstart.mapper"})
public class SearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class, args);
    }

}