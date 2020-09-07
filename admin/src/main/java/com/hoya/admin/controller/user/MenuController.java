package com.hoya.admin.controller.user;

import com.hoya.admin.constant.SysConstants;
import com.hoya.admin.model.sys.SysMenu;
import com.hoya.admin.server.sys.SysMenuService;
import com.hoya.admin.util.SecurityUtils;
import com.hoya.core.exception.AppExceptionForbidden;
import com.hoya.core.exception.AppExceptionServerError;
import com.hoya.core.exception.HttpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/menu")
public class MenuController {

    private Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping(value = "/findNavTree")
    public HttpResult findNavTree() {
        //获取当前登陆用户
        String userName = SecurityUtils.getUsername();
        if (null == userName) {
            throw new AppExceptionForbidden("用户尚未登陆");
        }

        try {
            List<SysMenu> res = null;
            if(SysConstants.ADMIN.equals(userName)) {
                res = sysMenuService.findNavTree();
            } else {
                res = sysMenuService.findNavTreeByUsername(userName);
            }
            return new HttpResult(res);
        } catch (Exception e) {
            logger.error(e.toString());
            throw new AppExceptionServerError("内部错误");
        }
    }
}
