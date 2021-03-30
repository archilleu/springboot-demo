package com.hoya.admin.controller.user;

import com.hoya.admin.security.JwtAuthenticatioToken;
import com.hoya.admin.util.SecurityUtils;
import com.hoya.admin.vo.LoginBean;
import com.hoya.core.exception.ServerException;
import com.hoya.core.utils.RequestParametersCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;

    @GetMapping("captcha.jpg")
    public void captcha(HttpServletResponse response, HttpServletRequest request) {
        throw ServerException.Found;
        //TODO:无状态验证码
    }

    /**
     * 登录接口
     */
    @PostMapping(value = "/login")
    public Object login(@RequestBody @Validated LoginBean loginBean, BindingResult bindingResult,
                            HttpServletRequest request) {
        RequestParametersCheck.check(bindingResult);

        String username = loginBean.getAccount();
        String password = loginBean.getPassword();

        // 系统登录认证
        JwtAuthenticatioToken token = SecurityUtils.login(request, username, password, authenticationManager);
        return token.getToken();
    }

}