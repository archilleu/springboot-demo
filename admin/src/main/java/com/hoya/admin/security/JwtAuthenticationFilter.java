package com.hoya.admin.security;

import com.alibaba.fastjson.JSON;
import com.hoya.admin.util.SecurityUtils;
import com.hoya.core.exception.ServerError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * 登录认证过滤器
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 获取token, 并检查登录状态
        try {
            SecurityUtils.checkAuthentication(request);
            chain.doFilter(request, response);
        } catch (Exception e) {
            ServerError error = new ServerError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
            String msg = JSON.toJSONString(error);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setCharacterEncoding("utf-8");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getOutputStream().write(msg.getBytes(StandardCharsets.UTF_8));
        }
    }

}