package com.yefeng.readingcloud.search.dao;

import com.yefeng.readingcloud.common.pojo.book.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author yefeng
 * @version 1.0.0
 * @ClassName BooksMapper.java
 * @Description TODO
 * @createTime 2022年07月09日 10:41:00
 */
@Repository
public interface BooksMapper extends BaseMapper<Book> {


    @Select("select * from book where book_id=#{bookId}")
    Book findBookById(@Param("bookId") Long bookId);



//    Book deleteBookById(@Param("bookId") int bookId);

//
////    @Update("update book set author_id=#{},dic_ ")
//    Book updateBookById(@Param("bookId") int bookId);

//    @Insert("insert into book values(#{authorId},#{dicCategory},#{dicChannel},#{dicSerialStatus}," +
//                                    "#{onlineStatus},#{bookId},#{bookName},#{bookScore},#{keyWord},#{imgUrl},#{authorName}" +
//                                    ",#{introduction},#{isbn},#{wordCount},#{creater},#{createTime},#{updateTime},#{updater}" +
//            ")")
//    Book saveBook(Book book);


}
