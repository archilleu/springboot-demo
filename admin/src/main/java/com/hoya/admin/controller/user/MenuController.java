package com.hoya.admin.controller.user;

import com.hoya.admin.constant.SysConstants;
import com.hoya.admin.model.sys.SysMenu;
import com.hoya.admin.server.sys.SysMenuService;
import com.hoya.admin.util.SecurityUtils;
import com.hoya.core.exception.ServerExceptionForbidden;
import com.hoya.core.exception.ServerExceptionServerError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user/menu")
public class MenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping(value = "/findNavTree")
    public List<SysMenu> findNavTree() {
        //获取当前登陆用户
        String userName = SecurityUtils.getUsername();
        if (null == userName) {
            throw new ServerExceptionForbidden("用户尚未登陆");
        }

        try {
            List<SysMenu> res = null;
            if(SysConstants.ADMIN.equals(userName)) {
                res = sysMenuService.findNavTree();
            } else {
                res = sysMenuService.findNavTreeByUsername(userName);
            }
            return res;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }
}
