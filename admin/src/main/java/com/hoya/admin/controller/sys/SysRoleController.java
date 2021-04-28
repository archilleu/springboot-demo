package com.hoya.admin.controller.sys;

import com.hoya.admin.constant.SysConstants;
import com.hoya.admin.model.sys.SysMenu;
import com.hoya.admin.model.sys.SysRole;
import com.hoya.admin.model.sys.SysRoleMenu;
import com.hoya.admin.server.sys.SysRoleService;
import com.hoya.core.exception.*;
import com.hoya.core.page.PageRequest;
import com.hoya.core.page.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @PreAuthorize("hasAuthority('sys:role:add') AND hasAuthority('sys:role:edit')")
    @PostMapping(value = "/save")
    public SysRole save(@RequestBody @Validated SysRole record) {
        if (SysConstants.ADMIN_ID.equals(record.getId())) {
            throw new ServerExceptionForbidden("超级管理员不允许修改!");
        }

        try {
            sysRoleService.save(record);
            return record;
        } catch (ServerException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }

    }

    @PreAuthorize("hasAuthority('sys:role:delete')")
    @PostMapping(value = "/delete")
    public void delete(@RequestBody List<SysRole> records) {
        //检擦是否含有超级管理员
        for(SysRole record : records) {
            if (SysConstants.ADMIN_ID.equals(record.getId())) {
                throw new ServerExceptionForbidden("超级管理员不允许删除!");
            }
        }

        try {
            sysRoleService.delete(records);
            return;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @PreAuthorize("hasAuthority('sys:role:view')")
    @PostMapping(value = "/findPage")
    public PageResult findPage(@RequestBody PageRequest pageRequest) {
        try {
            return sysRoleService.findPage(pageRequest);
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @PreAuthorize("hasAuthority('sys:role:view')")
    @GetMapping(value = "/findAll")
    public List<SysRole> findAll() {
        try {
            return sysRoleService.findAll();
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @PreAuthorize("hasAuthority('sys:role:view')")
    @GetMapping(value = "/findRoleMenus")
    public List<SysMenu> findRoleMenus(@RequestParam(required = true) Long roleId) {
        try {
            return sysRoleService.findRoleMenus(roleId);
        }catch (ServerExceptionNotFound e) {
            throw e;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @PreAuthorize("hasAuthority('sys:role:view') AND hasAuthority('sys:role:edit')")
    @PostMapping(value = "/saveRoleMenus")
    public Integer saveRoleMenus(@RequestBody List<SysRoleMenu> records) {
        try {
            return sysRoleService.saveRoleMenus(records);
        }catch (ServerExceptionNotFound e) {
            throw e;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

}
