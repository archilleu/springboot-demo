package com.hoya.admin.controller.user;

import com.hoya.admin.server.sys.SysUserService;
import com.hoya.admin.util.SecurityUtils;
import com.hoya.core.exception.ServerExceptionForbidden;
import com.hoya.core.exception.ServerExceptionServerError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Slf4j
@RestController
public class PermissionController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping(value = "/user/findPermission")
    public Set<String> findPermission() {
        //获取当前登陆用户
        String userName = SecurityUtils.getUsername();
        if (null == userName) {
            throw new ServerExceptionForbidden("用户尚未登陆");
        }

        try {
            Set<String> res = sysUserService.findPermissions(userName);
            return res;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }
}
