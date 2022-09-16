package com.example.api.controller;

import com.example.api.model.vo.LoginUserVo;
import com.example.auth.util.JwtUtil;
import com.example.auth.vo.LoginUser;
import com.example.auth.vo.TokenUser;
import com.example.common.base.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.HashSet;

/**
 * @author cjy
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理相关接口")
public class LoginController {

    @Log("登录")
    @Cacheable("key")
    @PostMapping("/login")
    @ApiOperation("用户登录接口")
    public String login(@Valid @RequestBody LoginUserVo loginUserVo) {
        // FIXME: 验证loginUser

        LoginUser loginUser = new LoginUser("id", loginUserVo.getName(), loginUserVo.getPassword());
        String token = JwtUtil.sign(loginUser, new HashSet<String>() {{
            add("admin");
        }}, new HashSet<String>() {{
            add("read");
        }});

        log.info(token);
        return token;
    }

    @Log
    @GetMapping("/info")
    public TokenUser info(@ApiIgnore TokenUser tokenUser) {
        return tokenUser;
    }
}
