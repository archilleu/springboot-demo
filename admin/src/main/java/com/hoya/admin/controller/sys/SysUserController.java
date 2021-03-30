package com.hoya.admin.controller.sys;

import com.hoya.admin.constant.SysConstants;
import com.hoya.admin.model.sys.SysUser;
import com.hoya.admin.server.sys.SysUserService;
import com.hoya.admin.util.PasswordUtils;
import com.hoya.admin.vo.SysUserRolesBean;
import com.hoya.core.exception.*;
import com.hoya.core.page.PageRequest;
import com.hoya.core.page.PageResult;
import com.hoya.core.utils.RequestParametersCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:user:add') AND hasAuthority('sys:user:edit')")
    public SysUser save(@RequestBody @Validated SysUser record, BindingResult bindingResult) {
        RequestParametersCheck.check(bindingResult);

        if (SysConstants.ADMIN_ID.equals(record.getId()))
            throw new ServerExceptionForbidden("不能修改超级管理员");

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
            return record;
        } catch (DuplicateKeyException e) {
            throw new ServerExceptionFound("用户已经存在");
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }

    }

    @PreAuthorize("hasAuthority('sys:user:delete')")
    @PostMapping(value = "/delete")
    public void delete(@RequestBody List<SysUser> records) {
        for (SysUser record : records) {
            if (SysConstants.ADMIN_ID.equals(record.getId()))
                throw new ServerExceptionForbidden("不能删除超级管理员");
        }

        try {
            sysUserService.delete(records);
            return;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @PreAuthorize("hasAuthority('sys:user:view')")
    @GetMapping(value = "/findPermissions")
    public Set<String> sfindPermissions(@RequestParam String name) {
        try {
            return sysUserService.findPermissions(name);
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @PostMapping("/saveUserRoles")
    @PreAuthorize("hasAuthority('sys:user:edit')")
    public void saveUserRole(@RequestBody @Validated SysUserRolesBean sysUserRolesBean, BindingResult bindingResult) {
        RequestParametersCheck.check(bindingResult);

        try {
            sysUserService.saveUserRoles(sysUserRolesBean);
            return;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @PreAuthorize("hasAuthority('sys:user:view')")
    @PostMapping(value = "/findPage")
    public PageResult findPage(@RequestBody PageRequest pageRequest) {
        try {
            return sysUserService.findPage(pageRequest);
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

}
