package com.yefeng.readingcloud.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 图书资源中心
 * @author yefeng
 */
//@EnableFeignClients
@SpringBootApplication(scanBasePackages={"com.yefeng.readingcloud.book", "com.yefeng.readingcloud.common"})
public class BookApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookApplication.class, args);
    }

}
