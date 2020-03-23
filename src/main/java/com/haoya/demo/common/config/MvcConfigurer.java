package com.haoya.demo.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfigurer implements WebMvcConfigurer {

    //拦截器
    public void addInterceptors(InterceptorRegistration registry) {
        //TODO：检测用户是否登录
    }

    //跨域访问配置
    public void addCorsMappings(CorsRegistry registry) {
    }

    //格式化
    public void addFormatters(FormatterRegistry registry) {
        /**
         * 日期字符串到Date类转换
         */
        registry.addFormatter(new DateFormatter("yyy-MM-dd HH:mm:ss"));
        //不用时分秒的可以在controller里面加入下面的格式绑定
        /*
        @InitBinder
        private void initBinder(WebDataBinder binder) {
            binder.addCustomFormatter(new DateFormatter("yyyy-MM-dd"));
        }
        */
    }

    //URI到视图的映射
    public void addViewControllers(ViewControllerRegistry registry) {
    }
}
