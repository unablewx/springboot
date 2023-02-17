package com.wx.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @Author:Mr.Wang
 * @Date: 2023/2/1  13:43
 * @Version 1.0
 */
public class Swagger3Config {
    @Bean
    public Docket apiConfig(){
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                //设置定位到需要生成文档接口的方式,定位了方法上的ApiOperation
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                //接口url路径,any代表全部的路径
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("springboot项目")
                .description("项目描述信息")
                .contact(new Contact("singleCycle","http://www.singleCycle.com","singleCycle@163.com"))
                .version("1.0")
                .build();
    }
}
