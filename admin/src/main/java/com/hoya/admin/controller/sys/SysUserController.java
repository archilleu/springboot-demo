package com.hoya.admin.controller.sys;

import com.hoya.admin.constant.SysConstants;
import com.hoya.admin.model.sys.SysUser;
import com.hoya.admin.server.sys.SysUserService;
import com.hoya.admin.util.PasswordUtils;
import com.hoya.core.exception.*;
import com.hoya.core.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:user:add') AND hasAuthority('sys:user:edit')")
    public HttpResult save(@RequestBody SysUser record) {
        SysUser user = sysUserService.findById(record.getId());
        if (null != user) {
            if (SysConstants.ADMIN.equalsIgnoreCase(user.getName())) {
                throw new AppExceptionForbidden("不能修改超级管理员");
            }
        }

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
            if(0 == sysUserService.save(record)) {
                throw new AppExceptionNotFound("角色不存在");
            }
        } catch (AppExceptionNotFound e) {
            throw e;
        } catch (DuplicateKeyException e) {
            throw new AppExceptionAreadyExists("用户名已经存在");
        } catch (Exception e) {
            throw new AppExceptionServerError("内部错误");
        }

        return HttpResult.OK;
    }

    @PreAuthorize("hasAuthority('sys:user:delete')")
    @PostMapping(value = "/delete")
    public HttpResult delete(@RequestBody List<SysUser> records) {
        for (SysUser record : records) {
            SysUser sysUser = sysUserService.findById(record.getId());
            if (sysUser != null && SysConstants.ADMIN.equalsIgnoreCase(sysUser.getName())) {
                throw new AppExceptionForbidden("级管理员不允许删除!");
            }
        }

        sysUserService.delete(records);
        return HttpResult.OK;
    }

    @PreAuthorize("hasAuthority('sys:user:view')")
    @GetMapping(value = "/findByName")
    public HttpResult findByUserName(@RequestParam String name) {
        return new HttpResult(sysUserService.findByName(name));
    }

    @PreAuthorize("hasAuthority('sys:user:view')")
    @GetMapping(value = "/findPermissions")
    public HttpResult findPermissions(@RequestParam String name) {
        return new HttpResult(sysUserService.findPermissions(name));
    }

    @PreAuthorize("hasAuthority('sys:user:view')")
    @GetMapping(value = "/findUserRoles")
    public HttpResult findUserRoles(@RequestParam Long userId) {
        return new HttpResult(sysUserService.findUserRoles(userId));
    }

    @PreAuthorize("hasAuthority('sys:user:view')")
    @PostMapping(value = "/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        return new HttpResult(sysUserService.findPage(pageRequest));
    }

}
