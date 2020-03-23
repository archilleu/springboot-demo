package com.haoya.demo.app.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sys/menu")
public class SysMenuController {

    @GetMapping("/index.html")
    public String sysMenu() {
        return "sys/sys_menu";
    }

}
