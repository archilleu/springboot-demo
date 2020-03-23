package com.haoya.demo.app.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sys")
public class LoginController {

    @GetMapping("/login")
    public String login(String username, String password) {
        return "login";
    }

}
