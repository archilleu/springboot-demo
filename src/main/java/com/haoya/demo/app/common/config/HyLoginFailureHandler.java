package com.haoya.demo.app.common.config;

import com.alibaba.fastjson.JSONObject;
import com.haoya.demo.app.exception.AppError;
import com.haoya.demo.app.exception.AppExceptionForbidden;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.haoya.demo.app.common.utils.RequestHelper.isJsonRequest;

@Configuration
public class HyLoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if(isJsonRequest(request)) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.write(JSONObject.toJSONString(new AppError(HttpStatus.FORBIDDEN.value(), "账号或者密码错误")));
            out.flush();
            out.close();
        } else {
            response.sendRedirect("/login");
        }
    }
}