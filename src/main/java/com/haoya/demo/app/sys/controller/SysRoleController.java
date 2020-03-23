package com.haoya.demo.app.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sys/role")
public class SysRoleController {

    @GetMapping("/index.html")
    public String sysRole() {
        return "sys/sys_role";
    }

}
