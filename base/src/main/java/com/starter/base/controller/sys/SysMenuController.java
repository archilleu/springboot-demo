package com.starter.base.controller.sys;

import com.starter.base.constant.SysConstants;
import com.starter.base.controller.dto.SysMenuDto;
import com.starter.base.controller.vo.MenuVo;
import com.starter.base.model.sys.SysMenu;
import com.starter.base.service.sys.SysMenuService;
import com.starter.base.shiro.util.TokenUser;
import com.starter.common.exception.ServerException;
import com.starter.common.exception.ServerExceptionServerError;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Api(tags = "菜单管理")
@RestController
@RequiresRoles(SysConstants.ADMIN)
@RequestMapping("/sys/menu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @RequiresPermissions(value = {"sys:menu:add"})
    @PostMapping(value = "/add")
    @ApiOperation(value = "添加菜单", notes = "返回菜单信息")
    public SysMenu add(@ApiIgnore TokenUser tokenUser,
                       @ApiParam(value = "菜单信息", required = true) @RequestBody @Validated SysMenuDto record) {
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(record, sysMenu);
        sysMenu.setCreateBy(tokenUser.getUsername());
        sysMenu.setCreateTime(LocalDateTime.now());
        try {
            sysMenuService.save(sysMenu);
            return sysMenu;
        } catch (DuplicateKeyException e) {
            throw ServerException.Found;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @RequiresPermissions(logical = Logical.AND, value = {"sys:menu:edit"})
    @PutMapping(value = "/{id}")
    @ApiOperation(value = "修改菜单", notes = "返回修改信息")
    public SysMenu edit(@ApiIgnore TokenUser tokenUser,
                        @ApiParam(value = "菜单id", required = true) @PathVariable Long id,
                        @ApiParam(value = "菜单信息", required = true) @RequestBody @Validated SysMenuDto record) {
        SysMenu sysMenu = sysMenuService.getById(id);
        if (null == sysMenu) {
            throw ServerException.NotFound;
        }
        BeanUtils.copyProperties(record, sysMenu);
        sysMenu.setLastUpdateBy(tokenUser.getUsername());
        sysMenu.setLastUpdateTime(LocalDateTime.now());
        try {
            sysMenuService.updateById(sysMenu);
            return sysMenu;
        } catch (DuplicateKeyException e) {
            throw ServerException.Found;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @RequiresPermissions(logical = Logical.AND, value = {"sys:menu:delete"})
    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "删除菜单", notes = "返回void")
    public void delete(@ApiParam(value = "删除菜单id", required = true) @PathVariable Long id) {
        try {
            sysMenuService.removeById(id);
        } catch (Exception e) {
            log.error(e.toString());
            throw ServerException.ServerError;
        }
    }

    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "批量删除菜单", notes = "返回void")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:menu:delete"})
    public void delete(@RequestBody List<Long> records) {
        try {
            sysMenuService.removeByIds(records);
            return;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @ApiOperation(value = "获取菜单数", notes = "返回菜单数")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:menu:view"})
    @GetMapping(value = "/tree")
    public List<MenuVo> tree() {
        try {
            return sysMenuService.getMenuTree();
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

}