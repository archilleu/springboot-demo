package com.hoya.admin.controller.sys;

import com.hoya.admin.model.sys.SysMenu;
import com.hoya.admin.server.sys.SysMenuService;
import com.hoya.core.exception.AppExceptionAreadyExists;
import com.hoya.core.exception.AppExceptionServerError;
import com.hoya.core.exception.HttpResult;
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
@RequestMapping("/sys/menu")
public class SysMenuController {

    private Logger logger = LoggerFactory.getLogger(SysMenuController.class);

    @Autowired
    private SysMenuService sysMenuService;

    @PreAuthorize("hasAuthority('sys:menu:add') AND hasAuthority('sys:menu:edit')")
    @PostMapping(value = "/save")
    public HttpResult save(@RequestBody @Validated SysMenu record, BindingResult bindingResult) {
        RequestParametersCheck.check(bindingResult);

        try {
            sysMenuService.save(record);
            return new HttpResult(record);
        } catch (DuplicateKeyException e) {
            throw new AppExceptionAreadyExists("菜单已经存在");
        } catch (Exception e) {
            logger.error(e.toString());
            throw new AppExceptionServerError("内部错误");
        }
    }

    @PreAuthorize("hasAuthority('sys:menu:delete')")
    @PostMapping(value = "/delete")
    public HttpResult delete(@RequestBody List<SysMenu> records) {
        try {
            sysMenuService.delete(records);
            return HttpResult.OK;
        } catch (Exception e) {
            logger.error(e.toString());
            throw new AppExceptionServerError("内部错误");
        }
    }

    @PreAuthorize("hasAuthority('sys:menu:view')")
    @GetMapping(value = "/findNavTree")
    public HttpResult findNavTree(@RequestParam String userName) {
        try {
            return new HttpResult(sysMenuService.findNavTreeByUsername(userName));
        } catch (Exception e) {
            logger.error(e.toString());
            throw new AppExceptionServerError("内部错误");
        }
    }

    @PreAuthorize("hasAuthority('sys:menu:view')")
    @PostMapping(value = "/findMenuTree")
    public HttpResult findMenuTree() {
        try {
            return new HttpResult(sysMenuService.findMenuTree());
        } catch (Exception e) {
            logger.error(e.toString());
            throw new AppExceptionServerError("内部错误");
        }
    }
}
