package com.haoya.demo.app;

import com.haoya.demo.app.sys.entity.SysParam;
import com.haoya.demo.app.sys.entity.SysUser;
import com.haoya.demo.app.sys.entity.SysUserDetails;
import com.haoya.demo.app.sys.repository.SysParamRepository;
import com.haoya.demo.common.config.ConfigParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

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
        SysParam sysParam = sysParamRepository.findByDomain(domain);
        if(null == sysParam) {
            sysParam= SysParam.DEFAULT();
        }

        model.addAttribute("logo", SysParam.kRESOURCE_MAIN+sysParam.getLogo());
        model.addAttribute("title", sysParam.getTitle());
        model.addAttribute("bg", SysParam.kRESOURCE_MAIN+sysParam.getBackgroundImage());
        model.addAttribute("copyright", sysParam.getCopyright());
        return sysParam.getLoginPage();
    }

    @GetMapping("favicon.ico")
    public String favicon(HttpServletRequest request) {
        String domain = request.getLocalName();
        SysParam sysParam = sysParamRepository.findByDomain(domain);
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
        SysParam sysParam = sysParamRepository.findByDomain(domain);
        if(null == sysParam) {
            sysParam= SysParam.DEFAULT();
        }

        model.addAttribute("logo", SysParam.kRESOURCE_MAIN+sysParam.getLogo());
        model.addAttribute("title", sysParam.getTitle());
        model.addAttribute("copyright", sysParam.getCopyright());
        model.addAttribute("homePage", sysParam.getHomePage());

        return "main.html";
    }

    @Autowired
    private SysParamRepository sysParamRepository;

    @Autowired
    ConfigParam configParam;

}
