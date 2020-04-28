package com.haoya.demo.app.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sys/param")
public class SysParamController {

    @GetMapping("/index.html")
    public String sysParam() {
        return "sys/sys_param";
    }

}
