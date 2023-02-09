package com.yefeng.readingcloud.homepage.common.config;

import com.yefeng.readingcloud.book.feign.client.BookClient;
import com.netflix.hystrix.*;
import feign.Feign;
import feign.hystrix.HystrixFeign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 图书资源中心 - 图书服务熔断配置
 * @author: yefeng
 * @since: 2019/9/25
 */
@Configuration
public class HystrixCommandBookConfig {

    @Bean
    public Feign.Builder bookFeignHystrixBuilder() {
        return HystrixFeign.builder().setterFactory((target, method) -> HystrixCommand.Setter
            // 组
            .withGroupKey(HystrixCommandGroupKey.Factory.asKey(BookClient.class.getSimpleName()))
            .andCommandKey(HystrixCommandKey.Factory.asKey(BookClient.class.getSimpleName()))
            .andCommandPropertiesDefaults(
                // 超时配置
                HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(500)
            )
            .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                    .withAllowMaximumSizeToDivergeFromCoreSize(true)
                    .withMaximumSize(5)
                    .withCoreSize(3)
                    .withMaxQueueSize(30)
            ));
    }
}
