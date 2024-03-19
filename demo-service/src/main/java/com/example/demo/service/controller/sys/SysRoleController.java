package com.example.demo.service.controller.sys;

import com.example.auth.vo.TokenUser;
import com.example.common.base.annotation.Log;
import com.example.common.base.vo.PageRequest;
import com.example.common.base.vo.PageResult;
import com.example.demo.service.constant.SysConstants;
import com.example.demo.service.model.entity.SysMenu;
import com.example.demo.service.model.entity.SysRole;
import com.example.demo.service.model.vo.input.SysRoleInputVo;
import com.example.demo.service.model.vo.input.SysRoleMenuInputVo;
import com.example.demo.service.model.vo.query.SysRoleQueryVo;
import com.example.demo.service.service.ISysRoleService;
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
 * 角色管理 前端控制器
 * </p>
 *
 * @author cjy
 * @since 2023-07-13
 */
@Slf4j
@Api(tags = "管理接口：角色管理接口")
@RestController
@RequiresRoles(SysConstants.ADMIN)
@RequestMapping("/sys/role")
public class SysRoleController {
    @Autowired
    private ISysRoleService sysRoleService;

    @Log
    @RequiresPermissions(logical = Logical.AND, value = {"sys:role:add"})
    @PostMapping(value = "/add")
    @ApiOperation(value = "添加角色", notes = "返回角色信息")
    public SysRole add(@ApiIgnore TokenUser tokenUser, @RequestBody @Validated SysRoleInputVo record) {
        return sysRoleService.add(tokenUser, record);
    }

    @Log
    @RequiresPermissions(logical = Logical.AND, value = {"sys:role:edit"})
    @PutMapping(value = "/{id}")
    @ApiOperation(value = "修改角色", notes = "返回修改信息")
    public SysRole edit(@ApiIgnore TokenUser tokenUser, @ApiParam(value = "角色id", required = true) @PathVariable Long id, @RequestBody @Validated SysRoleInputVo record) {
        return sysRoleService.edit(tokenUser, id, record);
    }

    @Log
    @RequiresPermissions(logical = Logical.AND, value = {"sys:role:delete"})
    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "删除角色", notes = "返回void")
    public void delete(@ApiIgnore TokenUser tokenUser, @ApiParam(value = "角色id", required = true) @PathVariable Long id) {
        sysRoleService.delete(tokenUser, id);
    }

    @Log
    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "批量删除角色", notes = "返回void")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:role:delete"})
    public void delete(@ApiIgnore TokenUser tokenUser, @RequestBody List<Long> records) {
        sysRoleService.delete(tokenUser, records);
    }

    @Log
    @ApiOperation(value = "获取角色列表", notes = "返回角色列表")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:role:view"})
    @PostMapping(value = "/list")
    public PageResult<SysRole> list(@ApiIgnore TokenUser tokenUser,
                                    @RequestBody @Validated PageRequest<SysRoleQueryVo> pageRequest) {
        return PageResult.newInstance(sysRoleService.findPage(tokenUser, pageRequest));
    }

    @Log
    @ApiOperation(value = "获取全部角色列表", notes = "返回角色列表")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:role:view"})
    @GetMapping(value = "/list-all")
    public List<SysRole> listAll() {
        return sysRoleService.list();
    }

    @Log
    @ApiOperation(value = "获取角色菜单列表", notes = "返回角色菜单列表")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:role:view"})
    @GetMapping(value = "/{id}/menus")
    public List<SysMenu> menus(@ApiIgnore TokenUser tokenUser,
                               @ApiParam(value = "角色id", required = true) @PathVariable Long id) {
        return sysRoleService.findRoleMenus(tokenUser, id);
    }

    @Log
    @ApiOperation(value = "保存角色菜单列表", notes = "返回void")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:role:edit"})
    @PostMapping(value = "/save-menus")
    public void saveRoleMenus(@ApiIgnore TokenUser tokenUser,
                              @RequestBody @Validated SysRoleMenuInputVo sysRoleMenuInputVo) {
        sysRoleService.saveRoleMenus(tokenUser, sysRoleMenuInputVo.getRoleId(), sysRoleMenuInputVo.getMenuIds());
    }
}
