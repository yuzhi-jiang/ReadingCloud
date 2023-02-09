package com.yefeng.readingcloud.book.mq;

import com.yefeng.readingcloud.book.service.BookService;
import com.yefeng.readingcloud.common.cache.RedisBookKey;
import com.yefeng.readingcloud.common.cache.RedisService;
import com.yefeng.readingcloud.common.constant.MqConstant;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author yefeng
 * @version 1.0.0
 * @ClassName MqConfig.java
 * @Description TODO
 * @createTime 2022年07月10日 21:15:00
 */
@Component
public class BookListener {
    @Resource
    private RedisService redisService;
    @Autowired
    private BookService bookService;
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = MqConstant.BOOK_INSERT_QUEUE),
                    exchange = @Exchange(value = MqConstant.BOOK_EXCHANGE, type = ExchangeTypes.TOPIC),
                    key = MqConstant.BOOK_INSERT_KEY
            ))
    public void listenBookInsertTopic(Long bookId) {
        System.out.println("book服务:收到bookId更新主题：bookid:" + bookId);
        //删除缓存
        System.out.println("开始删除缓存");
        redisService.removeCache(RedisBookKey.getBookKey(String.valueOf(bookId)));
        redisService.removeCache(RedisBookKey.getBookChapterKey(String.valueOf(bookId)));
        redisService.removeCache(RedisBookKey.getBookChapterNodeKey(String.valueOf(bookId)));
        redisService.removeCache(RedisBookKey.BookCenter.getFeignClientBookKey(String.valueOf(bookId)));
        //主动查询书本信息，更新缓存
        bookService.getBookById(String.valueOf(bookId));
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(
                    value = MqConstant.BOOK_DELETE_QUEUE),
            exchange = @Exchange(value = MqConstant.BOOK_EXCHANGE,type = ExchangeTypes.TOPIC),
            key = MqConstant.BOOK_DELETE_KEY))
    public void listenBookDeleteTopic(Long bookId) {
        System.out.println("book服务:收到bookId删除主题：bookid:" + bookId);
        //删除缓存
        redisService.removeCache(RedisBookKey.getBookKey(String.valueOf(bookId)));
        redisService.removeCache(RedisBookKey.getBookChapterKey(String.valueOf(bookId)));
        redisService.removeCache(RedisBookKey.getBookChapterNodeKey(String.valueOf(bookId)));
        redisService.removeCache(RedisBookKey.getBookChapterListKey(String.valueOf(bookId)));
        redisService.removeCache(RedisBookKey.BookCenter.getFeignClientBookKey(String.valueOf(bookId)));
    }
}
