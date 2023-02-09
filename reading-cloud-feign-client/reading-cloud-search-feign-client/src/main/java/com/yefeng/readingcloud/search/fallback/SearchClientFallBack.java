package com.yefeng.readingcloud.search.fallback;

import com.yefeng.readingcloud.common.result.Result;
import com.yefeng.readingcloud.common.result.ResultUtil;
import com.yefeng.readingcloud.search.client.SearchClient;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author yefeng
 * @version 1.0.0
 * @ClassName SearchClientFallBack.java
 * @Description TODO
 * @createTime 2022年07月09日 13:22:00
 */

@Component
public class SearchClientFallBack implements FallbackFactory<SearchClient> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchClientFallBack.class);

    @Override
    public SearchClient create(Throwable cause) {
        return new SearchClient() {
            @Override
            public Result getSearchResultBooks(String keyword, Integer page, Integer limit) {
                LOGGER.error("获取图书失败：{}", cause.getMessage());
                return ResultUtil.success(null);
            }

            @Override
            public Result getHotSearchWordList(Integer size) {
                LOGGER.error("获取图书失败：{}", cause.getMessage());
                return ResultUtil.success(null);
            }

//            @Override
//            public Result<Book> getBookById(String bookId) {
//                LOGGER.error("获取图书[{}]失败：{}", bookId, cause.getMessage());
//                return ResultUtil.success(null);
//            }
        };
    }
}