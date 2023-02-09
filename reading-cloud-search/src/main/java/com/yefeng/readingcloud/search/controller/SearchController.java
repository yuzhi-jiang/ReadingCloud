package com.yefeng.readingcloud.search.controller;

import com.yefeng.readingcloud.common.result.Result;
import com.yefeng.readingcloud.search.service.SearchService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 图书查询接口
 * @author: yefeng
 * @since: 2020/5/29
 */
@Api(value = "图书搜索接口")
@RestController
//@RequestMapping("search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @ApiOperation(value = "图书查询接口", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "keyword", value = "关键字", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "page", value = "页数", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int")
    })
    @ApiResponses({@ApiResponse(code = 200, message = "", response = String.class)})

    @GetMapping("searchBooks")
    public Result getSearchResultBooks(String keyword, Integer page, Integer limit){
        return this.searchService.getSearchResultBooks(keyword, page, limit);
    }



    @ApiOperation(value = "自动补全接口",httpMethod = "GET")
    @ApiImplicitParam(paramType = "query",name = "keyword",value = "关键字",required = true,dataType="string")
    @GetMapping("getSuggest")
    public Result getSuggestByKey(String keyword){
        return this.searchService.getSuggestResultBooks(keyword);
    }

    @ApiOperation(value = "获取热搜词接口", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "size", value = "返回数量", required = true, dataType = "int")
    })
    @ApiResponses({@ApiResponse(code = 200, message = "", response = String.class)})
    @GetMapping("getHotSearchWords")
    public Result getHotSearchWordList(Integer size){
        return this.searchService.getHotSearchWordList(size);
    }
}