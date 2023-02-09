package com.yefeng.readingcloud.homepage.dao;

import com.yefeng.readingcloud.common.pojo.index.IndexBooklistItem;

import java.util.List;

/**
 * 书单配置 - book
 * @author: yefeng
 * @since: 2020/4/6
 */
public interface IndexBooklistItemMapper {

    /**
     * 查询榜单全部图书
     * @param booklistId
     * @return
     */
    List<IndexBooklistItem> findPageWithResult(Integer booklistId);
}
