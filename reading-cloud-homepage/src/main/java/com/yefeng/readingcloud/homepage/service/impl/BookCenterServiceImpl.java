package com.yefeng.readingcloud.homepage.service.impl;

import com.yefeng.readingcloud.common.cache.RedisBookKey;
import com.yefeng.readingcloud.common.cache.RedisExpire;
import com.yefeng.readingcloud.common.cache.RedisService;
import com.yefeng.readingcloud.homepage.service.BookCenterService;
import com.yefeng.readingcloud.common.pojo.book.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yefeng.readingcloud.book.feign.client.BookClient;

/**
 * 图书中心服务
 * @author: yefeng
 * @since: 2019/7/4
 */
@Service
public class BookCenterServiceImpl implements BookCenterService {

    @Autowired
    private BookClient bookClient;

    @Autowired
    private RedisService redisService;

    @Override
    public Book getBookById(String bookId) {
        String key = RedisBookKey.BookCenter.getFeignClientBookKey(bookId);
        Book book = this.redisService.getCache(key, Book.class);
        if (book != null) {
            return book;
        }

        // 图书中心服务获取
        book = bookClient.getBookById(bookId).getData();
        if (book != null) {
            this.redisService.setExpireCache(key, book, RedisExpire.HOUR);
        }
        return book;
    }
}
