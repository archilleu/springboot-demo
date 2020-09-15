package com.hoya.admin.security;

import com.alibaba.fastjson.JSON;
import com.hoya.core.exception.AppError;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        AppError error = new AppError(HttpServletResponse.SC_FORBIDDEN, "认证失败");
        String msg = JSON.toJSONString(error);

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(msg);
        printWriter.flush();
        printWriter.close();
    }
}
