package com.yefeng.readingcloud.account.feign.fallback;

import com.yefeng.readingcloud.account.feign.client.LikeSeeClient;
import com.yefeng.readingcloud.common.result.Result;
import com.yefeng.readingcloud.common.result.ResultUtil;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 喜欢看客户端feign接口快速失败
 * @author: yefeng
 * @since: 2019/7/4
 */
@Component
public class LikeSeeClientFallBack implements FallbackFactory<LikeSeeClient> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LikeSeeClientFallBack.class);

    @Override
    public LikeSeeClient create(Throwable cause) {
        return new LikeSeeClient() {
            @Override
            public Result<Integer> getBookLikesCount(String bookId) {
                LOGGER.error("获取喜欢看[{}]数量失败：{}", bookId, cause.getMessage());
                return ResultUtil.success(0);
            }
        };
    }
}
