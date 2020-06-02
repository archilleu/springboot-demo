package com.haoya.demo.app.controller;

import com.haoya.demo.app.model.sys.SysParam;
import com.haoya.demo.app.model.sys.SysUser;
import com.haoya.demo.app.model.sys.SysUserDetails;
import com.haoya.demo.app.service.sys.SysParamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;

@Controller
@RequestMapping("/")
public class MainController {

    /**
     * 首页
     * @return
     */
    @GetMapping("home.html")
    public String home() {
        return "home";
    }

    /**
     * 登录页
     * @param model
     * @param request
     * @return
     * @throws FileNotFoundException
     */
    @GetMapping("login")
    public String login(Model model, HttpServletRequest request) throws FileNotFoundException {

        String domain = request.getLocalName();
        SysParam sysParam = sysParamMapper.findByDomain(domain);
        if(null == sysParam) {
            sysParam = SysParam.DEFAULT();
        }

        model.addAttribute("title", sysParam.getTitle());
        model.addAttribute("copyright", sysParam.getCopyright());
        return sysParam.getLoginPage();
    }

    @GetMapping("favicon.ico")
    public String favicon(HttpServletRequest request) {
        String domain = request.getLocalName();
        SysParam sysParam = sysParamMapper.findByDomain(domain);
        if(null == sysParam) {
            sysParam= SysParam.DEFAULT();
        }
        String path = "redirect:" + SysParam.kRESOURCE_MAIN + sysParam.getFavicon();
        return path;
    }

    /**
     * 主框架页
     * @return
     */
    @GetMapping("main")
    public String main(Model model, HttpServletRequest request) {
        SysUserDetails userDetails = (SysUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUser sysUser = userDetails.getSysUser();

        model.addAttribute(sysUser);

        String domain = request.getLocalName();
        SysParam sysParam = sysParamMapper.findByDomain(domain);
        if(null == sysParam) {
            sysParam= SysParam.DEFAULT();
        }

        model.addAttribute("title", sysParam.getTitle());
        model.addAttribute("copyright", sysParam.getCopyright());
        model.addAttribute("homePage", sysParam.getHomePage());

        return "main.html";
    }

    @Autowired
    private SysParamMapper sysParamMapper;

}
