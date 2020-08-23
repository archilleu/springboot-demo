package com.hoya.admin.security;

import com.hoya.admin.util.JwtTokenUtils;
import com.hoya.admin.util.SecurityUtils;
import com.hoya.core.exception.AppExceptionForbidden;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //预检不需要校验token
        if (HttpMethod.OPTIONS.equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        // 获取请求头token
        String token = JwtTokenUtils.getToken(request);
        if(StringUtils.isEmpty(token)) {
            throw new AppExceptionForbidden("token认证失败");
        }

        // 进行token认证
        SecurityUtils.checkAuthentication(request);

        //TODO request 加入当前用户信息
        return true;
    }
}
