package com.hoya.admin.controller.sys;

import com.hoya.admin.model.sys.SysMenu;
import com.hoya.admin.server.sys.SysMenuService;
import com.hoya.core.exception.ServerExceptionFound;
import com.hoya.core.exception.ServerExceptionServerError;
import com.hoya.core.utils.RequestParametersCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @PreAuthorize("hasAuthority('sys:menu:add') AND hasAuthority('sys:menu:edit')")
    @PostMapping(value = "/save")
    public SysMenu save(@RequestBody @Validated SysMenu record, BindingResult bindingResult) {
        RequestParametersCheck.check(bindingResult);

        try {
            sysMenuService.save(record);
            return record;
        } catch (DuplicateKeyException e) {
            throw new ServerExceptionFound("菜单已经存在");
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @PreAuthorize("hasAuthority('sys:menu:delete')")
    @PostMapping(value = "/delete")
    public void delete(@RequestBody List<SysMenu> records) {
        try {
            sysMenuService.delete(records);
            return;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @PreAuthorize("hasAuthority('sys:menu:view')")
    @PostMapping(value = "/getMenuTree")
    public List<SysMenu> findMenuTree() {
        try {
            return sysMenuService.getMenuTree();
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }
}
