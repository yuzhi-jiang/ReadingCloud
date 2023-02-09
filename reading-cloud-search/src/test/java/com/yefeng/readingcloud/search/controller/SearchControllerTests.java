package com.yefeng.readingcloud.search.controller;


import com.yefeng.readingcloud.common.constant.MqConstant;
import com.yefeng.readingcloud.common.pojo.book.Book;
import com.yefeng.readingcloud.search.dao.BooksMapper;
import com.yefeng.readingcloud.search.dao.HotSearchWordMapper;
import com.yefeng.readingcloud.search.dao.SearchBookItemMapper;
import com.yefeng.readingcloud.search.domain.SearchBookItem;
import com.alibaba.fastjson.JSON;


import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author yefeng
 * @version 1.0.0
 * @ClassName SearchControllerTest.java
 * @Description TODO
 * @createTime 2022年07月08日 14:52:00
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SearchControllerTests {


    @Resource
    RestHighLevelClient mRestHighLevelClient;

//    @Before
//    public void init() {
//        mRestHighLevelClient = new RestHighLevelClient(RestClient.builder(
//                HttpHost.create("http://127.0.0.1:9200")
//        ));
//    }

    @Test
    public void booksExitsTest() throws IOException {

        GetRequest request=new GetRequest("books");

        boolean books = mRestHighLevelClient.indices().exists(new GetIndexRequest("books"), RequestOptions.DEFAULT);
        System.out.println(books);
    }


    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void saveBookTest() {

//        SearchBookItem book = new SearchBookItem();
//
//        int len = booksMapper.insert(book);
//        if (len>0){
//            rabbitTemplate.convertAndSend(MqConstant.BOOK_EXCHANGE,book.getBookId());
//        }
//        System.out.println(len>0?"保存成功":"保存失败");
    }

    @Test

    public void updateBookById() {
        Long bookId=3001823523L;
//        rabbitTemplate.convertAndSend(MqConstant.BOOK_EXCHANGE,MqConstant.BOOK_INSERT_KEY,bookId);
//        rabbitTemplate.convertAndSend(MqConstant.BOOK_EXCHANGE,MqConstant.BOOK_DELETE_KEY,bookId);
        Book book = booksMapper.findBookById(bookId);
        book.setBookScore(6);
        int len = booksMapper.updateById(book);
        if (len>0){
            System.out.println("更新成功，调用更新es数据");
            rabbitTemplate.convertAndSend(MqConstant.BOOK_EXCHANGE,MqConstant.BOOK_INSERT_KEY,bookId);
        }
        else {
            System.out.println("更新书本失败 bookId:"+bookId);
        }
    }

    @Test
    public void deleteBookByIdTest() {
        Long bookId=806800381L;
//        int len = booksMapper.delete(new QueryWrapper<Book>().eq("book_id",bookId));
//        if (len>0){
//            System.out.println("删除书本成功 bookId:"+bookId);
            rabbitTemplate.convertAndSend(MqConstant.BOOK_EXCHANGE,MqConstant.BOOK_DELETE_KEY,bookId);
//        }
//        else System.out.println("删除书本失败 bookId:"+bookId);
    }

    @Test
    public void getAllBookTest() throws IOException {
        SearchRequest request=new SearchRequest("books");
        request.source().query(QueryBuilders.matchAllQuery());
        SearchResponse response = mRestHighLevelClient.search(request,RequestOptions.DEFAULT);
        System.out.println(response);
    }


    @Autowired
    BooksMapper booksMapper;

    @Autowired
    SearchBookItemMapper searchBookItemMapper;
    @Resource
    HotSearchWordMapper hotSearchWordMapper;

    @Test
    public void mybatisPlusTest() {
        List<SearchBookItem> books = searchBookItemMapper.findAllBookItems();
        System.out.println(books.size());
    }

    @Test
    public void addBooksTest() throws IOException {
        List<SearchBookItem> bookList = searchBookItemMapper.findAllBookItems();
        System.out.println(bookList.get(0));
        BulkRequest bulkRequest = new BulkRequest();
        bookList.forEach(searchBookItem -> {
            // 设置智能补全
            String keyWord = searchBookItem.getKeyWord();
            boolean flag=false;
            String splitChar="";
            if (keyWord.contains(",")){
                flag=true;
                splitChar=",";
            }
            if (keyWord.contains(" ")){
                flag=true;
                splitChar=" ";
            }
            if (flag){
                String[] split = keyWord.split(splitChar);
                ArrayList<String> strings = new ArrayList<>(split.length+3);
                strings.add(searchBookItem.getBookName());
                strings.add(searchBookItem.getAuthor());
                strings.add(searchBookItem.getCategoryName());
                Collections.addAll(strings,split);
                searchBookItem.setSuggestion(strings);
            }else {
                searchBookItem.setSuggestion(
                        Arrays.asList(searchBookItem.getBookName(),
                                        searchBookItem.getAuthor(),
                                        searchBookItem.getCategoryName()));
            }
            bulkRequest.add(new IndexRequest("books")
                    .id(searchBookItem.getBookId())
                    .source(JSON.toJSONString(searchBookItem), XContentType.JSON)
            );
        });
        mRestHighLevelClient.bulk(bulkRequest,RequestOptions.DEFAULT);
    }

    @Test
    public void testSuggest() throws IOException {
        String key="孟";
        SearchRequest request = new SearchRequest("books");

        request.source().suggest(new SuggestBuilder().addSuggestion("mysuggestion",
                SuggestBuilders.completionSuggestion("suggestion")
                        .prefix(key)
                        .skipDuplicates(true)
                        .size(10)));
        SearchResponse response = mRestHighLevelClient.search(request, RequestOptions.DEFAULT);

        System.out.println(response);
        Suggest suggest = response.getSuggest();
        CompletionSuggestion mysuggestion = suggest.getSuggestion("mysuggestion");
        mysuggestion.getOptions().forEach(option -> {
            String text = option.getText().toString();
            System.out.println(text);
        });
    }

    @Test
    public void getBooksBySearchKeyTest() throws IOException {
        String key="农民";


        SearchRequest request=new SearchRequest("books");


        request.source().query(QueryBuilders.matchQuery("all",key)).from(1).size(1);


        SearchResponse response = mRestHighLevelClient.search(request, RequestOptions.DEFAULT);
        System.out.println(response);

        SearchHits searchHits = response.getHits();

        System.out.println("hits.getTotalHits().条数 = " + searchHits.getTotalHits().value);

        SearchHit[] hits = searchHits.getHits();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            SearchBookItem bookItem = JSON.parseObject(sourceAsString, SearchBookItem.class);
            System.out.println(bookItem);
        }
    }

    @After
    public void close() throws IOException {
        mRestHighLevelClient.close();
    }
}
