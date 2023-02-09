package com.yefeng.readingcloud.search.client;

import com.yefeng.readingcloud.common.result.Result;
import com.yefeng.readingcloud.search.fallback.SearchClientFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author yefeng
 * @version 1.0.0
 * @ClassName SearchClient.java
 * @Description TODO
 * @createTime 2022年07月09日 13:22:00
 */
@FeignClient(contextId = "search", name = "light-reading-cloud-search", fallbackFactory = SearchClientFallBack.class)
public interface SearchClient {

    @GetMapping("/search/searchBooks")
     Result getSearchResultBooks(String keyword, Integer page, Integer limit);


    @GetMapping("/search/getHotSearchWords")
     Result getHotSearchWordList(Integer size);

}
