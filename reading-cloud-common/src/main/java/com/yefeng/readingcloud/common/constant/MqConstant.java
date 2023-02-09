package com.yefeng.readingcloud.common.constant;

/**
 * @author yefeng
 * @version 1.0.0
 * @ClassName MqConstant.java
 * @Description TODO
 * @createTime 2022年07月10日 21:16:00
 */
public class MqConstant {

    //交换机
    public final static String BOOK_EXCHANGE="book.topic";


    public final static String BOOK_INSERT_QUEUE="book.insert.queue";
    public static final String BOOK_INSERT_QUEUE_SEARCH = "book.insert.queue.search";


    //监听删除队列
    public final static String BOOK_DELETE_QUEUE="book.delete.queue";
    public final static String BOOK_DELETE_QUEUE_SEARCH="book.delete.queue.search";

    //增加或修改的routingKey
    public final static String BOOK_INSERT_KEY="book.insert";
    //删除的routingKey
    public final static String BOOK_DELETE_KEY="book.delete";



    //////////////////章节相关//////////////////////
    //交换机
    public final static String BOOK_CHAPTER_EXCHANGE="book.chapter.topic";

    //监听增加/修改队列
    public final static String BOOK_CHAPTER_INSERT_QUEUE="book.chapter.insert.queue";
    public final static String BOOK_CHAPTER_DELETE_QUEUE="book.chapter.delete.queue";

    //增加或修改的routingKey
    public final static String BOOK_CHAPTER_INSERT_KEY="book.chapter.insert";
    //删除的routingKey
    public final static String BOOK_CHAPTER_DELETE_KEY="book.chapter.delete";
}
