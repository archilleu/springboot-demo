package com.hoya.admin.controller.sys;

import com.google.code.kaptcha.Producer;
import com.hoya.admin.security.JwtAuthenticatioToken;
import com.hoya.admin.server.sys.SysUserService;
import com.hoya.admin.util.SecurityUtils;
import com.hoya.admin.vo.LoginBean;
import com.hoya.core.exception.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class SysLoginController {

    @Autowired
    private Producer producer;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    AuthenticationManager authenticationManager;

    @GetMapping("captcha.jpg")
    public void captcha(HttpServletResponse response, HttpServletRequest request) {
        //TODO:无状态验证码
        return;
    }

    /**
     * 登录接口
     */
    @PostMapping(value = "/login")
    public HttpResult login(@RequestBody LoginBean loginBean, HttpServletRequest request) throws IOException {
        String username = loginBean.getAccount();
        String password = loginBean.getPassword();

        // 系统登录认证
        JwtAuthenticatioToken token = SecurityUtils.login(request, username, password, authenticationManager);
        return new HttpResult(token);
    }

}