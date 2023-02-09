package com.yefeng.readingcloud.homepage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = {"com.yefeng.readingcloud.book.feign", "com.yefeng.readingcloud.account.feign"})
@SpringBootApplication(scanBasePackages = {"com.yefeng.readingcloud.homepage", "com.yefeng.readingcloud.common", "com.yefeng.readingcloud.book.feign", "com.yefeng.readingcloud.account.feign"})
@EnableHystrix
@EnableHystrixDashboard
public class HomepageApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomepageApplication.class, args);
    }

}
