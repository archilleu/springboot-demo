package com.haoya.demo.app.common.config;

import com.alibaba.fastjson.JSONObject;
import com.haoya.demo.app.exception.AppException;
import com.haoya.demo.app.exception.AppExceptionForbidden;
import com.haoya.demo.app.model.sys.SysUserDetails;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.haoya.demo.app.common.utils.RequestHelper.isJsonRequest;

@Configuration
public class HyLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if(isJsonRequest(request)) {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            SysUserDetails user = (SysUserDetails)authentication.getPrincipal();
            out.write(JSONObject.toJSONString(user));
            out.flush();
            out.close();
        } else {
            response.sendRedirect("/main");
        }
    }
}