package com.hoya.admin.security;

import com.alibaba.fastjson.JSON;
import com.hoya.core.exception.ServerError;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ServerError error = new ServerError(HttpServletResponse.SC_UNAUTHORIZED, "认证失败");
        String msg = JSON.toJSONString(error);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getOutputStream().write(msg.getBytes(StandardCharsets.UTF_8));
    }
}
