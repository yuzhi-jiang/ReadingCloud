package com.yefeng.readingcloud.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = {"com.yefeng.readingcloud.book.feign"})
@SpringBootApplication(scanBasePackages={ "com.yefeng.readingcloud.account", "com.yefeng.readingcloud.common", "com.yefeng.readingcloud.book.feign"})
public class AccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }

}
