package com.yefeng.readingcloud.search.mq;

import com.yefeng.readingcloud.common.constant.MqConstant;
import com.yefeng.readingcloud.search.service.SearchService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yefeng
 * @version 1.0.0
 * @ClassName MqConfig.java
 * @Description TODO
 * @createTime 2022年07月10日 21:15:00
 */
@Component
public class BookListener {
    @Autowired
    SearchService searchService;
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = MqConstant.BOOK_INSERT_QUEUE_SEARCH),
                    exchange = @Exchange(value = MqConstant.BOOK_EXCHANGE, type = ExchangeTypes.TOPIC),
                    key = MqConstant.BOOK_INSERT_KEY
            ))
    public void listenBookInsertTopic(Long bookId) {
        System.out.println("search服务:收到bookId更新主题：bookid:"+bookId);
        //  es搜索引擎更新索引（全量更新与插入为同一restAPi）
        searchService.insertById(bookId);
    }
    @RabbitListener(bindings = @QueueBinding(
                            value = @Queue(
                                    value = MqConstant.BOOK_DELETE_QUEUE_SEARCH),
                                    exchange = @Exchange(value = MqConstant.BOOK_EXCHANGE,type = ExchangeTypes.TOPIC),
                                    key = MqConstant.BOOK_DELETE_KEY))
    public void listenBookDeleteTopic(Long bookId) {
        System.out.println("search服务: 收到bookId删除主题：bookid:"+bookId);
        //搜索服务删除书本
        searchService.deleteById(bookId);
    }
}
