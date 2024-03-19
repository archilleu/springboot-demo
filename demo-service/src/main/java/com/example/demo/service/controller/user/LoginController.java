package com.example.demo.service.controller.user;

import com.example.common.base.annotation.Log;
import com.example.common.base.annotation.RateLimiter;
import com.example.common.base.exception.ServerException;
import com.example.demo.service.model.vo.LoginTokenVo;
import com.example.demo.service.model.vo.query.LoginQueryVo;
import com.example.demo.service.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author cjy
 */
@Slf4j
@Api(tags = "用户接口：用户登陆")
@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private ISysUserService sysUserService;

    @GetMapping("captcha.jpg")
    @ApiOperation(value = "验证码获取", notes = "返回验证码")
    public void captcha(@ApiIgnore HttpServletResponse response, @ApiIgnore HttpServletRequest request) {
        throw ServerException.Forbidden;
    }

    /**
     * 登录接口
     */
    @RateLimiter
    @Log("用户登录")
    @PostMapping(value = "/login")
    @ApiOperation(value = "用户登陆", notes = "返回用户登陆信息")
    public LoginTokenVo login(@RequestBody @Validated LoginQueryVo loginQo) {
        // 系统登录认证
        String token = sysUserService.login(loginQo.getAccount(), loginQo.getPassword());
        LoginTokenVo loginInfoVo = new LoginTokenVo();
        loginInfoVo.setToken(token);
        return loginInfoVo;
    }

}