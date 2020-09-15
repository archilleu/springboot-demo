package com.hoya.admin.controller.user;

import com.hoya.admin.server.sys.SysUserService;
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

import java.util.Set;

@RestController
public class PermissionController {
    private Logger logger = LoggerFactory.getLogger(PermissionController.class);

    @Autowired
    private SysUserService sysUserService;

    @GetMapping(value = "/user/findPermission")
    public HttpResult findPermission() {
        //获取当前登陆用户
        String userName = SecurityUtils.getUsername();
        if (null == userName) {
            throw new AppExceptionForbidden("用户尚未登陆");
        }

        try {
            Set<String> res = sysUserService.findPermissions(userName);
            return new HttpResult(res);
        } catch (Exception e) {
            logger.error(e.toString());
            throw new AppExceptionServerError("内部错误");
        }
    }
}
