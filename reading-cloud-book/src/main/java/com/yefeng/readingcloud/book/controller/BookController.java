package com.yefeng.readingcloud.book.controller;

import com.yefeng.readingcloud.book.service.BookService;
import com.yefeng.readingcloud.book.vo.BookVO;
import com.yefeng.readingcloud.common.pojo.book.Book;
import com.yefeng.readingcloud.common.result.Result;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 图书接口
 * @author: yefeng
 * @since: 2019/4/3
 */
@Api(value = "图书查询接口")
@RestController
//@RequestMapping("book")
public class BookController {

    @Autowired
    private BookService bookService;

    @ApiOperation(value = "查询图书基本信息" , httpMethod = "GET")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", name = "bookId", value = "图书ID", dataType = "String")
    })
    @ApiResponses({@ApiResponse(code = 200, message = "", response = Book.class)})
    @GetMapping("/getBookById")
    public Result<Book> getBookById(String bookId){
        return bookService.getBookById(bookId);
    }


    @ApiOperation(value = "获取图书详情" , httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "bookId", value = "图书ID", dataType = "String")
    })
    @ApiResponses({@ApiResponse(code = 200, message = "", response = Book.class)})
    @GetMapping("/details")
    public Result<BookVO> getBookDetails(String bookId){
        return bookService.getBookDetails(bookId);
    }
}