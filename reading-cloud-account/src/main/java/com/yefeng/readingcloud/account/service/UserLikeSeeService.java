package com.yefeng.readingcloud.account.service;

import com.yefeng.readingcloud.common.result.Result;

/**
 * 喜欢看服务
 * @author: yefeng
 * @since: 2020/4/15
 */
public interface UserLikeSeeService {

    /**
     * 喜欢点击处理
     * @param userId
     * @param bookId
     * @param value     0:取消喜欢 1:喜欢
     * @return
     */
    Result likeSeeClick(Integer userId, String bookId, Integer value);

    /**
     * 获取图书喜欢数量
     * <p>
     *     缓存20分钟
     * </p>
     * @param bookId
     * @return
     */
    Result<Integer> getBookLikesCount(String bookId);

    /**
     * 获取用户喜欢列表
     * @param userId
     * @param page
     * @param limit
     * @return
     */
    Result getUserLikeBookList(Integer userId, Integer page, Integer limit);

    /**
     * 用户是否喜欢此书
     * @param userId
     * @param bookId
     * @return
     */
    Result userLikeThisBook(Integer userId, String bookId);
}
