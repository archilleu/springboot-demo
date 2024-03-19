package com.example.common.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * @author cjy
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Value("${swagger.enable:true}")
    private boolean enable;
    @Value("${swagger.title:标题}")
    private String title;
    @Value("${swagger.description:描述}")
    private String description;
    @Value("${swagger.version:v1}")
    private String version;
    @Value("${swagger.contact.name:cjy}")
    private String name;
    @Value("${swagger.contact.url:http://www.google.com}")
    private String url;
    @Value("${swagger.contact.email:cjy@gmail.com}")
    private String email;
    @Value("${swagger.basePackage:com.example}")
    private String basePackage;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30).enable(enable)
                // token 请求头
                .globalRequestParameters(new LinkedList<RequestParameter>() {{
                    add(new RequestParameterBuilder().name("token").description("令牌").in(ParameterType.HEADER).build());
                }})
                //配置网站的基本信息
                .apiInfo(new ApiInfoBuilder()
                        //网站标题
                        .title(title)
                        //标题后面的版本号
                        .version(version).description(description)
                        //联系人信息
                        .contact(new Contact(name, url, email)).build()).select()
                //指定接口的位置
                .apis(RequestHandlerSelectors.basePackage(basePackage)).build().protocols(new HashSet<String>() {{
                    add("http");
                    add("https");
                }});
    }
}