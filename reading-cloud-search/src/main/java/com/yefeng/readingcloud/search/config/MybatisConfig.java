package com.yefeng.readingcloud.search.config;

import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * MyBatis配置
 * @author: yefeng
 * @since: 2020/4/2
 */
@Configuration
@MapperScan(basePackages = "com.yefeng.readingcloud.search.dao",
        sqlSessionTemplateRef="bookCenterSqlSessionTemplate")
public class MybatisConfig {

    private final static String MAPPER_LOCATIONS = "classpath*:mappers/*.xml";

    /** 工厂配置 */
    @Bean
    public SqlSessionFactory sqlSessionFactoryBean1(@Qualifier("bookCenterDataSource") DataSource dataSource) throws Exception {
        // 设置数据源
       // SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        //使用mybatis-plus
        MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
        factory.setDataSource(dataSource);

//        System.out.println(dataSource);
        // 添加XML映射


        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factory.setMapperLocations(resolver.getResources(MAPPER_LOCATIONS));

        //添加插件
        //factory.setPlugins(new Interceptor[]{ this.getPageHelper() });
        SqlSessionFactory sqlSessionFactory = factory.getObject();

        System.out.println(sqlSessionFactory);
        return sqlSessionFactory;
    }

    /** 会话模板 */
    @Bean(name = "bookCenterSqlSessionTemplate")
    public SqlSessionTemplate setSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /** 分页插件 */
    private PageHelper getPageHelper(){
        //配置分页插件，详情请查阅官方文档
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        //分页尺寸为0时查询所有纪录不再执行分页
        properties.setProperty("pageSizeZero", "true");
        //页码<=0 查询第一页，页码>=总页数查询最后一页
        properties.setProperty("reasonable", "false");
        //支持通过 Mapper 接口参数来传递分页参数
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("params", "count=countSql");
        //切换数据源，自动解析不同数据库的分页
        properties.setProperty("autoRuntimeDialect", "true");
        pageHelper.setProperties(properties);
        return pageHelper;
    }
}
