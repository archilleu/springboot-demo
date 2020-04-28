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
@RequestMapping("/sys/user")
public class SysUserController {

    @GetMapping("/index.html")
    public String sysUser() {
        return "sys/sys_user";
    }

    /**
     * 获取用户信息
     * @param model
     */
    @ModelAttribute
    private void userInfoModel(Model model) {
        SysUserDetails userDetails = (SysUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUser sysUser = userDetails.getSysUser();

        model.addAttribute(sysUser);
    }

}
