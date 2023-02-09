package com.yefeng.readingcloud.homepage.service;

import com.yefeng.readingcloud.common.pojo.book.Book;

/**
 * 图书中心服务
 * @author: yefeng
 * @since: 2019/7/4
 */
public interface BookCenterService {

    /**
     * 获取图书详情
     * @param bookId
     * @return
     */
    Book getBookById(String bookId);
}
