package com.yefeng.readingcloud.book.service;

import com.yefeng.readingcloud.book.vo.BookVO;
import com.yefeng.readingcloud.common.result.Result;

/**
 * 图书服务
 * @author: yefeng
 * @since: 2019/7/4
 */
public interface BookService {

    /**
     * 查询图书基本信息
     * @param bookId
     * @return
     */
    Result getBookById(String bookId);





    /**
     * 获取图书详情
     * @param bookId
     * @return
     */
    Result<BookVO> getBookDetails(String bookId);
}
