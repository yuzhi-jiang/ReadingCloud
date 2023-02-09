package com.yefeng.readingcloud.search.dao;

import com.yefeng.readingcloud.search.domain.SearchBookItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yefeng
 * @version 1.0.0
 * @ClassName SearchBookItemMapper.java
 * @Description TODO
 * @createTime 2022年07月11日 12:48:00
 */
@Repository
public interface SearchBookItemMapper {
    //    @Select("select * from book")
    List<SearchBookItem> findAllBookItems();
    SearchBookItem findBookItemByBookId(@Param("bookId") Long bookId);
}
