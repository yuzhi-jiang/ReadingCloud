package com.yefeng.readingcloud.gateway.controller;

import com.yefeng.readingcloud.common.result.Result;
import com.yefeng.readingcloud.common.result.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 快速失败接口
 * @author: yefeng
 * @since: 2020/4/11
 */
public class FallbackController {

    @GetMapping("/fallback")
    public Result fallback() {
        return ResultUtil.fail();
    }
}
