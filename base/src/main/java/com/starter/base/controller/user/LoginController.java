package com.starter.base.controller.user;

import com.starter.base.controller.dto.LoginDto;
import com.starter.base.controller.vo.LoginInfoVo;
import com.starter.base.service.sys.SysUserService;
import com.starter.common.exception.ServerException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Api(tags = "用户登陆")
@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("captcha.jpg")
    @ApiOperation(value = "验证码获取", notes = "返回验证码")
    public void captcha(@ApiIgnore HttpServletResponse response, @ApiIgnore HttpServletRequest request) {
        //TODO:无状态验证码
        throw ServerException.Forbidden;
    }

    /**
     * 登录接口
     */
    @PostMapping(value = "/login")
    @ApiOperation(value = "用户登陆", notes = "返回用户登陆信息")
    public LoginInfoVo login(@ApiParam(value = "用户登陆账号密码", required = true) @RequestBody @Validated LoginDto loginRequest) {

        String username = loginRequest.getAccount();
        String password = loginRequest.getPassword();

        // 系统登录认证
        String token = sysUserService.login(username, password);
        LoginInfoVo loginInfoVo = new LoginInfoVo();
        loginInfoVo.setToken(token);
        return loginInfoVo;
    }

}