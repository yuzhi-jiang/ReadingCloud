package com.yefeng.readingcloud.homepage.dao;

import com.yefeng.readingcloud.common.pojo.index.IndexPageConfig;
import java.util.List;

/**
 * 主页配置
 * @author: yefeng
 * @since: 2020/4/5
 */
public interface IndexPageConfigMapper {

    /**
     * 主键查询实体
     * @param id
     * @return
     */
    IndexPageConfig selectById(Integer id);

    /***
     * 查询主页配置
     * @param pageType
     * @return
     */
    List<IndexPageConfig> findPageWithResult(Integer pageType);
}
