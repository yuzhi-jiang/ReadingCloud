package com.yefeng.readingcloud.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yefeng
 * @version 1.0.0
 * @ClassName RestHighLevelConfig.java
 * @Description TODO
 * @createTime 2022年07月09日 12:23:00
 */
//@Configuration
@RefreshScope
//@ConfigurationProperties(prefix = "spring.thread-pool.bookshelf")
public class RestHighLevelConfig {
    private static Logger LOGGER = LoggerFactory.getLogger(JestConfig.class);

    @Value("${es.servers}")
    private String es_servers;

    @Bean
    public RestHighLevelClient RestHighLevClient() {

        String[] sers = es_servers.split(",");

        System.out.println("RestHighLevClient ：len:"+sers.length);

        HttpHost[] httpHosts=new HttpHost[sers.length];
        //集群
        for (int i = 0; i < sers.length; i++) {
            httpHosts[i]=HttpHost.create(sers[i]);
        }
        return new RestHighLevelClient(RestClient.builder(
                httpHosts
        ));
    }
}
