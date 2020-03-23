package com.haoya.demo.app.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sys/user")
public class SysUserController {

    @GetMapping("/index.html")
    public String sysUser() {
        return "sys/sys_user";
    }

}
