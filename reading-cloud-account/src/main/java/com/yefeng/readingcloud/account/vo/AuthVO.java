package com.yefeng.readingcloud.account.vo;

import lombok.Data;

/**
 * 认证VO
 * @author: yefeng
 * @since: 2020/4/14
 */
@Data
public class AuthVO {
    private String token;
    private UserVO user;
}
