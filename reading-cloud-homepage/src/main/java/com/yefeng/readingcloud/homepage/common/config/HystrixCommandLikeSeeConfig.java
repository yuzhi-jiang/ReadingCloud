package com.yefeng.readingcloud.homepage.common.config;

/**
 * 账户中心 - 喜欢看服务熔断配置
 * @author: yefeng
 * @since: 2019/9/25

@Configuration
public class HystrixCommandLikeSeeConfig {

    @Bean
    public Feign.Builder likeSeeFeignHystrixBuilder() {
        return HystrixFeign.builder().setterFactory((target, method) -> HystrixCommand.Setter
            // 组
            .withGroupKey(HystrixCommandGroupKey.Factory.asKey(LikeSeeClient.class.getSimpleName()))
            .andCommandKey(HystrixCommandKey.Factory.asKey(LikeSeeClient.class.getSimpleName()))
            .andCommandPropertiesDefaults(
                // 超时配置
                HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(500)
            )
            .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                    .withAllowMaximumSizeToDivergeFromCoreSize(true)
                    .withMaximumSize(3)
                    .withCoreSize(2)
                    .withMaxQueueSize(20)
            ));
    }
}
 */