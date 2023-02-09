package com.yefeng.readingcloud.book.dao;

import com.yefeng.readingcloud.common.pojo.book.Book;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 图书
 * @author: yefeng
 * @since: 2020/3/16
 */

public interface BookMapper {

    Book selectById(Integer id);



    Book selectByBookId(String bookId);

    List<Book> findPageWithResult(@Param("dicCategory") Integer dicCategory,
                                  @Param("dicChannel") Integer dicChannel,
                                  @Param("dicSerialStatus") Integer dicSerialStatus,
                                  @Param("onlineStatus") Integer onlineStatus,
                                  @Param("authorId") Integer authorId,
                                  @Param("bookId") String bookId,
                                  @Param("bookName") String bookName);

    Integer findPageWithCount(@Param("dicCategory") Integer dicCategory,
                              @Param("dicChannel") Integer dicChannel,
                              @Param("dicSerialStatus") Integer dicSerialStatus,
                              @Param("onlineStatus") Integer onlineStatus,
                              @Param("authorId") Integer authorId,
                              @Param("bookId") String bookId,
                              @Param("bookName") String bookName);
}
