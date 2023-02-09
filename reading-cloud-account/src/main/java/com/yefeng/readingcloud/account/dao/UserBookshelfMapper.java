package com.yefeng.readingcloud.account.dao;

import com.yefeng.readingcloud.common.pojo.account.UserBookshelf;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户书架
 * @author: yefeng
 * @since: 2020/4/10
 */
public interface UserBookshelfMapper {

    int deleteById(Integer id);
    int deleteByBookId(String bookId,int userId);

    int insert(UserBookshelf userBookshelf);

    int updateByUserIdAndBookId(UserBookshelf userBookshelf);

    int selectCountByUserAndBookId(@Param("userId") Integer userId,
                                   @Param("bookId") String bookId);

    UserBookshelf selectById(Integer id);

    List<UserBookshelf> findPageWithResult(@Param("userId") Integer userId);
}
