package com.yefeng.readingcloud.homepage.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 书单图书VO
 * @author: yefeng
 * @since: 2020/4/7
 */
@Data
public class BooklistBookVO implements Serializable {

    private static final Long serialVersionUID = 1L;

    private String bookId;
    private String bookName;
    private String introduction;
    private String imgUrl;
    private Integer authorId;
    private String authorName;
    private String categoryName;
    private Integer wordCount;
    private Integer bookScore;
    /** 连载状态 */
    private String serialStatusName;
    /** 喜欢看数量 */
    private Integer likeCount;
}
