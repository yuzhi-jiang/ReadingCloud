package com.yefeng.readingcloud.book.mq;

import com.yefeng.readingcloud.book.service.BookChapterService;
import com.yefeng.readingcloud.common.cache.RedisBookKey;
import com.yefeng.readingcloud.common.cache.RedisService;
import com.yefeng.readingcloud.common.constant.MqConstant;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yefeng
 * @version 1.0.0
 * @ClassName BookChapterListener.java
 * @Description TODO
 * @createTime 2022年07月20日 18:44:00
 */
@Component
public class BookChapterListener {


    @Resource
    BookChapterService bookChapterService;
    @RabbitListener(bindings =@QueueBinding(
            value = @Queue(value = MqConstant.BOOK_CHAPTER_INSERT_QUEUE),
            exchange = @Exchange(value = MqConstant.BOOK_EXCHANGE, type = ExchangeTypes.TOPIC),
            key = MqConstant.BOOK_CHAPTER_INSERT_KEY
    ))
    public void updateBookChapterQueue(String bookId, Integer chapterId) {
        removeCache(Long.valueOf(bookId));
//        bookChapterService.getChapterById(bookId, chapterId);
    }

//    删除书本所有章节监听
    @RabbitListener(bindings =@QueueBinding(
            value = @Queue(value = MqConstant.BOOK_DELETE_QUEUE),
            exchange = @Exchange(value = MqConstant.BOOK_EXCHANGE, type = ExchangeTypes.TOPIC),
            key = MqConstant.BOOK_DELETE_KEY
    ))
    public void deleteBookQueue(String bookId) {
        removeCache(Long.valueOf(bookId));
    }

    @Resource
    RedisService redisService;
    public void removeCache(Long bookId) {
        Set<String> keys = new HashSet<>();
        keys.add(RedisBookKey.getBookChapterNodeKey(String.valueOf(bookId)));
        keys.add(RedisBookKey.getBookChapterKey(String.valueOf(bookId)));

        redisService.removeList(keys);
    }
}
