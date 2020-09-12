package com.hoya.admin.controller.sys;

import com.hoya.admin.constant.SysConstants;
import com.hoya.admin.model.sys.SysRole;
import com.hoya.admin.model.sys.SysRoleMenu;
import com.hoya.admin.server.sys.SysRoleService;
import com.hoya.core.exception.*;
import com.hoya.core.page.PageRequest;
import com.hoya.core.utils.RequestParametersCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys/role")
public class SysRoleController {

    private Logger logger = LoggerFactory.getLogger(SysRoleController.class);

    @Autowired
    private SysRoleService sysRoleService;

    @PreAuthorize("hasAuthority('sys:role:add') AND hasAuthority('sys:role:edit')")
    @PostMapping(value = "/save")
    public HttpResult save(@RequestBody @Validated SysRole record, BindingResult bindingResult) {
        RequestParametersCheck.check(bindingResult);

        if (SysConstants.ADMIN_ID.equals(record.getId())) {
            throw new AppExceptionForbidden("超级管理员不允许修改!");
        }

        try {
            sysRoleService.save(record);
            return new HttpResult(record);
        } catch (DuplicateKeyException e) {
            throw new AppExceptionAreadyExists("角色已经存在");
        } catch (Exception e) {
            logger.error(e.toString());
            throw new AppExceptionServerError("内部错误");
        }

    }

    @PreAuthorize("hasAuthority('sys:role:delete')")
    @PostMapping(value = "/delete")
    public HttpResult delete(@RequestBody List<SysRole> records) {
        //检擦是否含有超级管理员
        for(SysRole record : records) {
            if (SysConstants.ADMIN_ID.equals(record.getId())) {
                throw new AppExceptionForbidden("超级管理员不允许删除!");
            }
        }

        try {
            sysRoleService.delete(records);
            return HttpResult.OK;
        } catch (Exception e) {
            logger.error(e.toString());
            throw new AppExceptionServerError("内部错误");
        }
    }

    @PreAuthorize("hasAuthority('sys:role:view')")
    @PostMapping(value = "/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        try {
            return new HttpResult(sysRoleService.findPage(pageRequest));
        } catch (Exception e) {
            logger.error(e.toString());
            throw new AppExceptionServerError("内部错误");
        }
    }

    @PreAuthorize("hasAuthority('sys:role:view')")
    @GetMapping(value = "/findAll")
    public HttpResult findAll() {
        try {
            return new HttpResult(sysRoleService.findAll());
        } catch (Exception e) {
            logger.error(e.toString());
            throw new AppExceptionServerError("内部错误");
        }
    }

    @PreAuthorize("hasAuthority('sys:role:view')")
    @GetMapping(value = "/findRoleMenus")
    public HttpResult findRoleMenus(@RequestParam(required = true) Long roleId) {
        try {
            return new HttpResult(sysRoleService.findRoleMenus(roleId));
        }catch (AppExceptionNotFound e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.toString());
            throw new AppExceptionServerError("内部错误");
        }
    }

    @PreAuthorize("hasAuthority('sys:role:view') AND hasAuthority('sys:role:edit')")
    @PostMapping(value = "/saveRoleMenus")
    public HttpResult saveRoleMenus(@RequestBody List<SysRoleMenu> records) {
        try {
            return new HttpResult(sysRoleService.saveRoleMenus(records));
        }catch (AppExceptionNotFound e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.toString());
            throw new AppExceptionServerError("内部错误");
        }
    }

}
