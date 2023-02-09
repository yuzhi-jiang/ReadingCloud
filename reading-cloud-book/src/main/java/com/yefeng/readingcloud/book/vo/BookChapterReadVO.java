package com.yefeng.readingcloud.book.vo;

import lombok.Data;

/**
 * 章节阅读VO
 * @author: yefeng
 * @since: 2020/5/6
 */
@Data
public class BookChapterReadVO {
    private BookChapterVO current;
    private BookChapterVO pre;
    private BookChapterVO next;
}
