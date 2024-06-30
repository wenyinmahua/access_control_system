package com.tyut.accesscontrol.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * Knife4j 配置
 *
 * @author mahua
 */
@Configuration
@EnableSwagger2WebMvc
@Profile({"dev"})
public class Knife4jConfig {

    @Bean(value = "defaultApi")
    public Docket defaultApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("XXX系统")
                        .description("# XXX系统")
                        .contact(new Contact("author","https://github.com/wenyinmahua/springbootinit","501847822@qq.com"))
                        .version("1.0")
                        .build())
                //分组名称
//                .groupName("1.0版本")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.tyut.accesscontrol.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}