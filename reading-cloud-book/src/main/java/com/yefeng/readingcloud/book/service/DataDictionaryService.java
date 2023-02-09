package com.yefeng.readingcloud.book.service;

import com.yefeng.readingcloud.common.pojo.book.DataDictionary;
import java.util.Map;

/**
 * 字典服务
 * @author: yefeng
 * @since: 2020/5/16
 */
public interface DataDictionaryService {
    Map<String, DataDictionary> getDictionarys(String dicType, String field);
}
