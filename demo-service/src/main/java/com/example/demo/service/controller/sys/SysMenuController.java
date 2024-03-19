package com.example.demo.service.controller.sys;

import com.example.auth.vo.TokenUser;
import com.example.common.base.annotation.Log;
import com.example.demo.service.constant.SysConstants;
import com.example.demo.service.model.entity.SysMenu;
import com.example.demo.service.model.vo.MenuInfoVo;
import com.example.demo.service.model.vo.input.SysMenuInputVo;
import com.example.demo.service.service.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * <p>
 * 角色菜单 前端控制器
 * </p>
 *
 * @author cjy
 * @since 2023-07-13
 */
@Slf4j
@Api(tags = "管理接口：菜单管理接口")
@RestController
@RequiresRoles(SysConstants.ADMIN)
@RequestMapping("/sys/menu")
public class SysMenuController {

    @Autowired
    private ISysMenuService sysMenuService;

    @Log
    @RequiresPermissions(value = {"sys:menu:add"})
    @PostMapping(value = "/add")
    @ApiOperation(value = "添加菜单", notes = "返回菜单信息")
    public SysMenu add(@ApiIgnore TokenUser tokenUser,
                       @RequestBody @Validated SysMenuInputVo record) {
        return sysMenuService.add(tokenUser, record);
    }

    @Log
    @RequiresPermissions(logical = Logical.AND, value = {"sys:menu:edit"})
    @PutMapping(value = "/{id}")
    @ApiOperation(value = "修改菜单", notes = "返回修改信息")
    public SysMenu edit(@ApiIgnore TokenUser tokenUser,
                        @ApiParam(value = "菜单id", required = true) @PathVariable Long id,
                        @RequestBody @Validated SysMenuInputVo record) {
        return sysMenuService.edit(tokenUser, id, record);
    }

    @Log
    @RequiresPermissions(logical = Logical.AND, value = {"sys:menu:delete"})
    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "删除菜单", notes = "返回void")
    public void delete(@ApiIgnore TokenUser tokenUser,
                       @ApiParam(value = "删除菜单id", required = true) @PathVariable Long id) {
        sysMenuService.delete(tokenUser, id);
    }

    @Log
    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "批量删除菜单", notes = "返回void")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:menu:delete"})
    public void delete(@ApiIgnore TokenUser tokenUser, @RequestBody List<Long> records) {
        sysMenuService.delete(tokenUser, records);
    }

    @Log
    @ApiOperation(value = "获取菜单数", notes = "返回菜单数")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:menu:view"})
    @GetMapping(value = "/tree")
    public List<MenuInfoVo> tree(@ApiIgnore TokenUser tokenUser) {
        return sysMenuService.getMenuTree(tokenUser);
    }
}
