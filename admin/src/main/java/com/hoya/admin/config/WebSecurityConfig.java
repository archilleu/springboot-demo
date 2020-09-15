package com.hoya.admin.config;

import com.hoya.admin.security.JwtAccessDeniedHandler;
import com.hoya.admin.security.JwtAuthenticationEntryPoint;
import com.hoya.admin.security.JwtAuthenticationFilter;
import com.hoya.admin.security.JwtAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 参考
 * https://zhuanlan.zhihu.com/p/74345791
 * https://www.ruanyifeng.com/blog/2018/07/json_web_token-tutorial.html
 * https://zhuanlan.zhihu.com/p/152224669
 * https://www.cnblogs.com/xifengxiaoma/p/10020960.html
 */
@Configuration
@EnableWebSecurity    // 开启Spring Security
@EnableGlobalMethodSecurity(prePostEnabled = true)    // 开启方法权限注解，如：@PreAuthorize注解
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 使用自定义身份验证组件
        auth.authenticationProvider(new JwtAuthenticationProvider(userDetailsService));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 禁用 csrf, 由于使用的是JWT，不需要csrf
        http.cors().and().csrf().disable()
            .authorizeRequests()
            // 跨域预检请求
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            // web jars
            .antMatchers("/webjars/**").permitAll()
            // 查看SQL监控（druid）
            .antMatchers("/druid/**").permitAll()
            // 首页和登录页面
            .antMatchers("/").permitAll()
            .antMatchers("/user/login").permitAll()
            // swagger
            .antMatchers("/swagger-ui.html").permitAll()
            .antMatchers("/swagger-resources/**").permitAll()
            .antMatchers("/v2/api-docs").permitAll()
            .antMatchers("/webjars/springfox-swagger-ui/**").permitAll()
            // 验证码
            .antMatchers("/user/captcha.jpg**").permitAll()
            // 服务监控
            .antMatchers("/actuator/**").permitAll()
            // 其他所有请求需要身份认证
            .anyRequest().authenticated();

        //异常处理
        http.exceptionHandling().accessDeniedHandler(new JwtAccessDeniedHandler()).authenticationEntryPoint(new JwtAuthenticationEntryPoint());

        // token验证过滤器
        http.addFilterBefore(new JwtAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}