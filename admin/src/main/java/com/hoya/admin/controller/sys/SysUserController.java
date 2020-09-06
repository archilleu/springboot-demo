package com.hoya.admin.controller.sys;

import com.hoya.admin.constant.SysConstants;
import com.hoya.admin.model.sys.SysUser;
import com.hoya.admin.server.sys.SysUserService;
import com.hoya.admin.util.PasswordUtils;
import com.hoya.core.exception.*;
import com.hoya.core.page.PageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    private Logger logger = LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:user:add') AND hasAuthority('sys:user:edit')")
    public HttpResult save(@RequestBody SysUser record) {
        if (SysConstants.ADMIN_ID.equals(record.getId()))
            throw new AppExceptionForbidden("不能修改超级管理员");

        SysUser user = sysUserService.findById(record.getId());
        if (record.getPassword() != null) {
            String salt = PasswordUtils.getSalt();
            if (user == null) {
                // 新增
                String password = PasswordUtils.encode(record.getPassword(), salt);
                record.setSalt(salt);
                record.setPassword(password);
            } else {
                // 修改用户

                // 修改了密码
                if (!record.getPassword().equals(user.getPassword())) {
                    String password = PasswordUtils.encode(record.getPassword(), salt);
                    record.setSalt(salt);
                    record.setPassword(password);
                }
            }
        }

        try {
            sysUserService.save(record);
            return HttpResult.OK;
        } catch (DuplicateKeyException e) {
            throw new AppExceptionAreadyExists("用户已经存在");
        } catch (Exception e) {
            logger.error(e.toString());
            throw new AppExceptionServerError("内部错误");
        }

    }

    @PreAuthorize("hasAuthority('sys:user:delete')")
    @PostMapping(value = "/delete")
    public HttpResult delete(@RequestBody List<SysUser> records) {
        for (SysUser record : records) {
            if (SysConstants.ADMIN_ID.equals(record.getId()))
                throw new AppExceptionForbidden("不能删除超级管理员");
        }

        try {
            sysUserService.delete(records);
            return HttpResult.OK;
        } catch (Exception e) {
            logger.error(e.toString());
            throw new AppExceptionServerError("内部错误");
        }
    }

    @PreAuthorize("hasAuthority('sys:user:view')")
    @GetMapping(value = "/findByUsername")
    public HttpResult findByUsername(@RequestParam String name) {
        try {
            return new HttpResult(sysUserService.findByName(name));
        } catch (Exception e) {
            logger.error(e.toString());
            throw new AppExceptionServerError("内部错误");
        }
    }

    @PreAuthorize("hasAuthority('sys:user:view')")
    @GetMapping(value = "/findPermissions")
    public HttpResult findPermissions(@RequestParam String name) {
        try {
            return new HttpResult(sysUserService.findPermissions(name));
        } catch (Exception e) {
            logger.error(e.toString());
            throw new AppExceptionServerError("内部错误");
        }
    }

    @PreAuthorize("hasAuthority('sys:user:view')")
    @GetMapping(value = "/findUserRoles")
    public HttpResult findUserRoles(@RequestParam Long userId) {
        try {
            return new HttpResult(sysUserService.findUserRoles(userId));
        } catch (Exception e) {
            logger.error(e.toString());
            throw new AppExceptionServerError("内部错误");
        }
    }

    @PreAuthorize("hasAuthority('sys:user:view')")
    @PostMapping(value = "/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        try {
            return new HttpResult(sysUserService.findPage(pageRequest));
        } catch (Exception e) {
            logger.error(e.toString());
            throw new AppExceptionServerError("内部错误");
        }
    }

}
