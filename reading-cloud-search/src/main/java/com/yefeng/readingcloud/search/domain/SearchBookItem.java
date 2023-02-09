package com.yefeng.readingcloud.search.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.junit.Ignore;

import java.util.List;

/**
 * Book搜索结果项
 * @author: yefeng
 * @since: 2020/5/29
 */
@Data
public class SearchBookItem {
    /**
     * 图书id
     */
    private String bookId;

    /**
     * 图书名称
     */
    private String bookName;

    /**
     * 图书评分
     */
    private Integer bookScore;

    /**
     * 封面
     */
    private String imgUrl;

    /**
     * 作者名称
     */
    private String author;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 分类
     */
    private Integer dicCategory;

    private String categoryName;

//    关键字
    private String keyWord;

//    用于搜索自动补全
    @TableField(exist = false)
    @JsonIgnore
    private List<String> suggestion;



    /*
    PUT /books
{

    "settings": {
    "analysis": {
      "analyzer": {
        "my_analyzer": {
          "tokenizer": "ik_smart",
          "filter": "py"
        },
        "completion_analyzer": {
          "tokenizer": "keyword",
          "filter": "py"
        }
      },
      "filter": {
        "py": {
          "type": "pinyin",
		      "keep_full_pinyin": false,
          "keep_joined_full_pinyin": true,
          "keep_original": true,
          "limit_first_letter_length": 16,
          "remove_duplicated_term": true,
          "none_chinese_pinyin_tokenize": false
        }
      }
    }
  },

  "mappings": {
    "properties": {
      "all":{
        "type": "text",
        "analyzer": "my_analyzer",
        "search_analyzer": "ik_smart"
      },

      "id":{
        "type": "keyword"
      },
      "keyWord":{
        "type": "text",
        "analyzer": "my_analyzer",
        "search_analyzer": "ik_smart",
        "copy_to": "all"
      },
      "bookName":{
        "type": "text",
         "analyzer": "my_analyzer",
        "search_analyzer": "ik_smart",
        "copy_to": "all"
      },
      "bookScore":{
        "type": "keyword",
        "index": "false"
      },
      "imgUrl":{
        "type": "keyword",
        "index": "false"
      },
      "author":{
        "type": "text",
        "analyzer": "my_analyzer",
        "search_analyzer": "ik_smart",
        "copy_to": "all"
      },
      "introduction":{
        "type": "text"
      },
      "dicCategory":{
        "type": "integer",
        "index": false
      },
      "categoryName":{
        "type": "text",
        "analyzer": "my_analyzer",
        "search_analyzer": "ik_smart",
        "copy_to": "all"
      },
       "suggestion":{
          "type": "completion",
          "analyzer": "completion_analyzer",
          "search_analyzer": "ik_smart"
      }
    }
  }
}

以下实现 自动补全的时候，精确前缀匹配
PUT /books
{

    "settings": {
    "analysis": {
      "analyzer": {
        "my_analyzer": {
          "tokenizer": "ik_smart",
          "filter": "py"
        },
        "completion_analyzer": {
          "tokenizer": "keyword",
          "filter": "py"
        },
        "completion_analyzer2": {
          "tokenizer": "ik_smart",
          "filter": "py2"
        },
        "pinyin_chinese_analyzer": {
          "tokenizer": "pinyin_tokenizer"
        },
        "pinyin_analyzer": {
          "tokenizer": "pinyin_chinese_tokenizer"
        }
      },
      "tokenizer": {
        "pinyin_chinese_tokenizer": {
          "type": "pinyin",
          "keep_first_letter": false,
          "keep_separate_first_letter": false,
          "keep_full_pinyin":false,
          "keep_original":false,
          "limit_first_letter_length":50,
          "keep_separate_chinese": true,
          "lowercase":true

        },
        "pinyin_tokenizer": {
          "type": "pinyin",
          "keep_first_letter": false,
          "keep_separate_first_letter": true,
          "keep_full_pinyin":true,
          "keep_original":false,
          "limit_first_letter_length":50,
          "keep_separate_chinese": true,
          "lowercase":true
        }
      },
      "filter": {
        "py": {
          "type": "pinyin",
		      "keep_full_pinyin": false,
          "keep_joined_full_pinyin": true,
          "keep_original": true,
          "limit_first_letter_length": 16,
          "remove_duplicated_term": true,
          "none_chinese_pinyin_tokenize": false
        },
        "py2":{
          "type": "pinyin",
          "keep_first_letter": false,
          "keep_separate_first_letter": false,
          "keep_full_pinyin":true,
          "keep_original":false,
          "limit_first_letter_length":50,
          "keep_separate_chinese": true,
          "lowercase":true
        }
      }
    }
  },

  "mappings": {
    "properties": {
      "all":{
        "type": "text",
        "analyzer": "pinyin_analyzer",
        "search_analyzer": "pinyin_chinese_analyzer"
      },

      "id":{
        "type": "keyword"
      },
      "keyWord":{
        "type": "text",
        "analyzer": "pinyin_analyzer",
        "search_analyzer": "pinyin_chinese_analyzer",
        "copy_to": "all"
      },
      "bookName":{
        "type": "text",
        "analyzer": "pinyin_analyzer",
        "search_analyzer": "pinyin_chinese_analyzer",
        "copy_to": "all"
      },
      "bookScore":{
        "type": "keyword",
        "index": "false"
      },
      "imgUrl":{
        "type": "keyword",
        "index": "false"
      },
      "author":{
        "type": "text",
        "analyzer": "pinyin_analyzer",
        "search_analyzer": "pinyin_chinese_analyzer",
        "copy_to": "all"
      },
      "introduction":{
        "type": "text"
      },
      "dicCategory":{
        "type": "integer",
        "index": false
      },
      "categoryName":{
        "type": "text",
        "analyzer": "pinyin_analyzer",
        "search_analyzer": "pinyin_chinese_analyzer",
        "copy_to": "all"
      },
       "suggestion":{
          "type": "completion",
        "analyzer": "completion_analyzer2",
        "search_analyzer": "ik_smart"
      }
    }
  }
}



     */
}
