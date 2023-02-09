package com.yefeng.readingcloud.book.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger 配置类
 * http://localhost:8080/swagger-ui.html
 * @author yefeng
 * @since 2019-07-04
 */
@Configuration
@EnableSwagger2
public class BookSwaggerConfig {
    /**
     * swagger生成
     * @return Docket
     */
    @Bean
    public Docket bookDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(true)
                .apiInfo(apiInfo())
                .select()
                // swagger 扫描 controller 包路径
                .apis(RequestHandlerSelectors.basePackage("com.yefeng.readingcloud.book.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    /**
     * swagger基础信息
     * @return ApiInfo swagger信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("图书中心接口")
                .description("图书中心")
                .termsOfServiceUrl("")
                .contact(new Contact("", "", ""))
                .license("")
                .licenseUrl("")
                .version("1.0.0")
                .build();
    }
}
