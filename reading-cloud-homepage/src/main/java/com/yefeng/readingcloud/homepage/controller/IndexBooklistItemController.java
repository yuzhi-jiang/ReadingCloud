package com.yefeng.readingcloud.homepage.controller;

import com.yefeng.readingcloud.common.result.Result;
import com.yefeng.readingcloud.homepage.service.IndexBooklistItemService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 书单图书接口
 * @author: yefeng
 * @since: 2020/4/8
 */
@Api(value = "精品页书单图书接口")
@RestController
public class IndexBooklistItemController {

    @Autowired
    private IndexBooklistItemService indexBooklistItemService;

    @ApiOperation(value = "书单更多分页接口", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "booklistId", value = "书单ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "page", value = "页数", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "Integer")
    })
    @ApiResponses({@ApiResponse(code = 200, message = "", response = String.class)})
    @GetMapping("getBooklistPagingBooks")
    public Result getBooklistPagingBooks(Integer booklistId, Integer page, Integer limit) {
        return this.indexBooklistItemService.getBooklistPagingBooks(booklistId, page, limit);
    }

    @ApiOperation(value = "书单换一换接口", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "booklistId", value = "书单ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "clientRandomNumber", value = "客户端当前书单编号", required = true, dataType = "Integer")
    })
    @ApiResponses({@ApiResponse(code = 200, message = "", response = String.class)})
    @GetMapping("getBooklistExchange")
    public Result getBooklistExchange(Integer booklistId, Integer clientRandomNumber) {
        return this.indexBooklistItemService.getBooklistExchange(booklistId, clientRandomNumber);
    }
}
