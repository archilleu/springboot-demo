package com.hoya.app.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.hoya.app.demo.entity.User;

/**

 * @ClassName: MvcConfigurer

 * @Description: 全局定制化Spring Boot的MVC特性

 */

@Configuration
public class MvcConfigurer implements WebMvcConfigurer {

	/*
	 * 拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//检擦会话URL以admin开头的都使用这个拦截器
		registry.addInterceptor(new HandlerInterceptor() {
			
			@Override
			public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
				throws Exception {
				User user = (User) request.getSession().getAttribute("user");
				if(null == user) {
					response.sendRedirect("/login.html");
					return false;
				}
				
				return true;
			}
			
			@Override
			public void postHandle(HttpServletRequest request, HttpServletResponse response, Object Handler,
					ModelAndView modelAndView) throws Exception {
				//controller方法处理完毕后调用此方法
			}
			
			@Override
			public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object Handler,
					Exception e) throws Exception {
				//页面渲染完成后调用,通常用于清楚某些资源，类似 finally语法
			}
		}).addPathPatterns("/admin/**");
	}
	
	/*
	 * 跨域访问配置
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		//允许所有跨域访问
		registry.addMapping("/**");
	}
	
	/*
	 * 格式化
	 */
	@Override
	public void addFormatters(FormatterRegistry registry) {
		//日期类型参数格式,没起到作用，使用JacksonConf配置
		//registry.addFormatter(new DateFormatter("yyyy-MM-dd HH:mm:ss"));
	}
	
	/*
	 * URI 到视图的映射
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		
	}
}
