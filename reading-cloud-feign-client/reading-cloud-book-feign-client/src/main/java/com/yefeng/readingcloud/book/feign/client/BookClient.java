package com.yefeng.readingcloud.book.feign.client;

import com.yefeng.readingcloud.book.feign.fallback.BookClientFallBack;
import com.yefeng.readingcloud.common.pojo.book.Book;
import com.yefeng.readingcloud.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 图书客户端feign接口
 *
 * @author: yefeng
 * @since: 2019/7/4
 */
@FeignClient(contextId = "book", name = "light-reading-cloud-book", fallbackFactory = BookClientFallBack.class)
public interface BookClient {

    @RequestMapping("/book/getBookById")
    Result<Book> getBookById(@RequestParam("bookId") String bookId);
}