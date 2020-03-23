package com.haoya.demo.app.sys.controller;

import com.haoya.demo.app.sys.entity.SysUser;
import com.haoya.demo.app.sys.entity.SysUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @ModelAttribute
    public void userInfoModel(Model model) {
        SysUserDetails userDetails = (SysUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUser sysUser = userDetails.getSysUser();

        model.addAttribute(sysUser);
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

}
