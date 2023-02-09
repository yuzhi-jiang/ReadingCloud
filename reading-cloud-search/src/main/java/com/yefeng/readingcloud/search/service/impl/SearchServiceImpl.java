package com.yefeng.readingcloud.search.service.impl;

import com.yefeng.readingcloud.common.result.Result;
import com.yefeng.readingcloud.common.result.ResultUtil;
import com.yefeng.readingcloud.search.dao.HotSearchWordMapper;
import com.yefeng.readingcloud.search.dao.SearchBookItemMapper;
import com.yefeng.readingcloud.search.domain.SearchBookItem;
import com.yefeng.readingcloud.search.domain.SearchBookResult;
import com.yefeng.readingcloud.search.service.SearchService;
import com.alibaba.fastjson.JSON;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 图书查询服务
 * @author: yefeng
 * @since: 2020/5/29
 */

@RefreshScope //实时更新
@Service
public class SearchServiceImpl implements SearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchServiceImpl.class);

    /** ES Jest 客户端对象 */
//    @Resource
    private JestClient jestClient;


    @Resource
    private RestHighLevelClient highLevelClient;

    /** 索引别名 */
    @Value("${es.aliasName}")
    private String aliasName;

    /** 类型 */
    @Value("${es.indexType}")
    private String indexType;

    @Resource
    private HotSearchWordMapper hotSearchWordMapper;

    @Override
    public Result getHotSearchWordList(Integer size) {
        List<String> hotSearchWordList = this.hotSearchWordMapper.getHotSearchWordList(size);
        return ResultUtil.success(hotSearchWordList);
    }

    @Override
    public Result getSearchResultBooks(String keyword, Integer page, Integer limit){
        // 查询条件
        SearchBookResult result = new SearchBookResult();
        try {
            SearchRequest request=new SearchRequest(aliasName);

            request.source().query(QueryBuilders.matchQuery("all",keyword)).from((page-1)*limit).size(limit);

            SearchResponse response = highLevelClient.search(request, RequestOptions.DEFAULT);

            SearchHits searchHits = response.getHits();
            long len = searchHits.getTotalHits().value;
            System.out.println("hits.getTotalHits().条数 = " + len);

            SearchHit[] hits = searchHits.getHits();

            List<SearchBookItem> bookList=new ArrayList<SearchBookItem>();;
            for (SearchHit hit : hits) {
                String sourceAsString = hit.getSourceAsString();
                SearchBookItem bookItem = JSON.parseObject(sourceAsString, SearchBookItem.class);
                bookList.add(bookItem);
//                System.out.println(bookItem);
            }
            result.setTotal(len);
            result.setBookList(bookList);
            return ResultUtil.success(result);

        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("查询图书异常");
        }
        return ResultUtil.fail().buildMessage("查询图书异常");
//        Map query = new HashMap();
//        // 多字段匹配
//        Map multiMatch = new HashMap();
//        multiMatch.put("query", keyword);
//        multiMatch.put("type", "most_fields");
//        String[] fields = new String[]{"bookName^2","bookName.pinyin","author"};
//        multiMatch.put("fields", fields);
//        query.put("multi_match",multiMatch);
//
//        int from = (page - 1) * limit;
//        int size = from + limit;
//        RequestQuery requestQuery = new RequestQuery(from, size, query);
//        SearchBookResult searchBookResult = this.getSearchResult(requestQuery.toString());
//        return ResultUtil.success(searchBookResult);
    }



    @Autowired
    SearchBookItemMapper searchBookItemMapper;

    @Override
    public Result getSuggestResultBooks(String keyword) {
        try {
            SearchRequest request = new SearchRequest("books");
            request.source().suggest(new SuggestBuilder().addSuggestion("mysuggestion",
                    SuggestBuilders.completionSuggestion("suggestion")
                            .prefix(keyword)
                            .skipDuplicates(true)
                            .size(10)));
            SearchResponse response = highLevelClient.search(request, RequestOptions.DEFAULT);

            Suggest suggest = response.getSuggest();
            CompletionSuggestion mysuggestion = suggest.getSuggestion("mysuggestion");

            ArrayList<String> strings = new ArrayList<>(mysuggestion.getOptions().size());
            mysuggestion.getOptions().forEach(option -> {
                String text = option.getText().toString();
                strings.add(text);
            });
            return ResultUtil.success().buildData(strings);
        } catch (Exception e) {
            ResultUtil.fail().buildMessage("服务丢失啦,清稍后再试!!");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertById(Long bookId) {
        try {
            System.out.println("es引擎触发更新/修改操作 bookId:"+bookId);


//            QueryWrapper<SearchBookItem> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("book_id",bookId);
            SearchBookItem book = searchBookItemMapper.findBookItemByBookId(bookId);
            System.out.println("book:");
            System.out.println(book);

            IndexRequest request =new IndexRequest("books")
                    .id(book.getBookId())
                    .source(JSON.toJSONString(book), XContentType.JSON);

            IndexResponse index = highLevelClient.index(request, RequestOptions.DEFAULT);
            System.out.println(index);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Long bookId) {
        try {
            System.out.println("es引擎触发删除操作 bookId:"+bookId);
            DeleteRequest request = new DeleteRequest(aliasName, String.valueOf(bookId));
            highLevelClient.delete(request,RequestOptions.DEFAULT);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ES 执行查询结果
     * @param query
     * @return
     */
    private SearchBookResult getSearchResult(String query){
        SearchBookResult result = new SearchBookResult();
        // 封装查询对象
        Search search = new Search.Builder(query)
                .addIndex(aliasName)
                .addType(indexType).build();

        // 执行查询
        try {
            SearchResult searchResult = this.jestClient.execute(search);
            List<SearchBookItem> bookList;
            if (searchResult.isSucceeded()) {
                // 查询成功，处理结果项
                List<SearchResult.Hit<SearchBookItem, Void>> hitList = searchResult.getHits(SearchBookItem.class);
                bookList = new ArrayList<>(hitList.size());
                for (SearchResult.Hit<SearchBookItem, Void> hit : hitList) {
                    bookList.add(hit.source);
                }
            } else {
                bookList = new ArrayList<>();
            }

            // 赋值
            result.setTotal(searchResult.getTotal());
            result.setBookList(bookList);
        } catch (IOException e) {
            LOGGER.error("查询图书异常，查询语句:{}", query, e);
        }
        return result;
    }
}
