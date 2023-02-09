package com.yefeng.readingcloud.homepage.dao;

import com.yefeng.readingcloud.common.pojo.index.IndexBannerItem;

import java.util.List;

/**
 * Banner项
 * @author: yefeng
 * @since: 2020/4/6
 */
public interface IndexBannerItemMapper {

    IndexBannerItem selectById(Integer id);

    /**
     * 按banner查询明细列表
     * @param bannerId
     * @return
     */
    List<IndexBannerItem> findPageWithResult(Integer bannerId);
}
