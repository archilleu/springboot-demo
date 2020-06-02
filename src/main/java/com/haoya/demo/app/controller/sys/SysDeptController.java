package com.haoya.demo.app.controller.sys;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sys/department")
public class SysDeptController {

    @GetMapping("/index.html")
    public String sysMenu() {
        return "sys/sys_department";
    }

}
