package com.yefeng.readingcloud.search;

import com.yefeng.readingcloud.search.dao.SearchBookItemMapper;
import com.yefeng.readingcloud.search.domain.SearchBookItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author yefeng
 * @version 1.0.0
 * @ClassName TestApplication.java
 * @Description TODO
 * @createTime 2022年07月11日 10:17:00
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestApplication {
    @Autowired
    SearchBookItemMapper searchBookItemMapper;
    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<SearchBookItem> userList = searchBookItemMapper.findAllBookItems();

        System.out.println(userList.size());
    }
}
