package com.yefeng.readingcloud.account.feign.client;

import com.yefeng.readingcloud.account.feign.fallback.LikeSeeClientFallBack;
import com.yefeng.readingcloud.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 喜欢看客户端feign接口
 * @author: yefeng
 * @since: 2019/7/4
 */
@FeignClient(contextId = "like", name = "light-reading-cloud-account", fallbackFactory = LikeSeeClientFallBack.class)
public interface LikeSeeClient {

    @GetMapping("/account/like-see/get-count")
    Result<Integer> getBookLikesCount(@RequestParam("bookId") String bookId);

}