package com.yefeng.readingcloud.search.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 热搜词
 * @author: yefeng
 * @since: 2020/5/29
 */
@Repository
public interface HotSearchWordMapper {

    /**
     * 获取热搜词
     * @param size
     * @return
     */
    @Select("SELECT name FROM hot_search_word order by frequency desc limit #{size}")
    List<String> getHotSearchWordList(@Param("size") int size);
}
