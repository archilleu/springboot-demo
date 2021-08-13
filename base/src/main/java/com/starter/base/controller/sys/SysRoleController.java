package com.starter.base.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.starter.base.constant.SysConstants;
import com.starter.base.controller.dto.SysRoleDto;
import com.starter.base.model.sys.SysMenu;
import com.starter.base.model.sys.SysRole;
import com.starter.base.model.sys.SysRoleMenu;
import com.starter.base.service.sys.SysRoleService;
import com.starter.base.shiro.util.TokenUser;
import com.starter.common.exception.ServerException;
import com.starter.common.exception.ServerExceptionServerError;
import com.starter.common.page.PageHelper;
import com.starter.common.page.PageRequest;
import com.starter.common.page.PageResult;
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
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Api(tags = "角色管理")
@RestController
@RequiresRoles(SysConstants.ADMIN)
@RequestMapping("/sys/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @RequiresPermissions(logical = Logical.AND, value = {"sys:role:add"})
    @PostMapping(value = "/add")
    @ApiOperation(value = "添加角色", notes = "返回角色信息")
    public SysRole add(@ApiIgnore TokenUser tokenUser,
                       @ApiParam(value = "角色信息", required = true) @RequestBody @Validated SysRoleDto record) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(record, sysRole);
        sysRole.setCreateBy(tokenUser.getUsername());
        sysRole.setCreateTime(LocalDateTime.now());
        try {
            sysRoleService.save(sysRole);
            return sysRole;
        } catch (DuplicateKeyException e) {
            throw ServerException.Found;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @RequiresPermissions(logical = Logical.AND, value = {"sys:role:edit"})
    @PutMapping(value = "/{id}")
    @ApiOperation(value = "修改角色", notes = "返回修改信息")
    public SysRole edit(@ApiIgnore TokenUser tokenUser,
                        @ApiParam(value = "角色id", required = true) @PathVariable Long id,
                        @ApiParam(value = "角色信息", required = true) @RequestBody @Validated SysRoleDto record) {
        SysRole sysRole = sysRoleService.getById(id);
        if (null == sysRole) {
            throw ServerException.NotFound;
        }
        BeanUtils.copyProperties(record, sysRole);
        sysRole.setLastUpdateBy(tokenUser.getUsername());
        sysRole.setLastUpdateTime(LocalDateTime.now());
        try {
            sysRoleService.updateById(sysRole);
            return sysRole;
        } catch (DuplicateKeyException e) {
            throw ServerException.Found;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @RequiresPermissions(logical = Logical.AND, value = {"sys:role:delete"})
    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "删除角色", notes = "返回void")
    public void delete(@ApiParam(value = "角色id", required = true) @PathVariable Long id) {
        try {
            sysRoleService.deleteById(id);
        } catch (Exception e) {
            log.error(e.toString());
            throw ServerException.ServerError;
        }
    }

    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "批量删除角色", notes = "返回void")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:role:delete"})
    public void delete(@RequestBody List<Long> records) {
        try {
            sysRoleService.deleteByIds(records);
            return;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @ApiOperation(value = "获取角色列表", notes = "返回角色列表")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:role:view"})
    @PostMapping(value = "/list")
    public PageResult list(@ApiParam(value = "列表请求参数, params{name}", required = true) @RequestBody PageRequest pageRequest) {
        try {
            QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
            Object name = pageRequest.getParam("name");
            if (!ObjectUtils.isEmpty(name)) {
                queryWrapper.like("name", name);
            }
            return PageHelper.findPage(pageRequest, sysRoleService, queryWrapper);
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @ApiOperation(value = "获取全部角色列表", notes = "返回角色列表")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:role:view"})
    @GetMapping(value = "/list-all")
    public List<SysRole> listAll() {
        try {
            return sysRoleService.findAll();
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @ApiOperation(value = "获取角色菜单列表", notes = "返回角色菜单列表")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:role:view"})
    @GetMapping(value = "/{id}/menus")
    public List<SysMenu> menus(@ApiParam(value = "角色id", required = true) @PathVariable(required = true) Long id) {
        try {
            return sysRoleService.findRoleMenus(id);
        } catch (ServerException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @ApiOperation(value = "保存角色菜单列表", notes = "返回void")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:role:edit"})
    @PostMapping(value = "/save-menus")
    public void saveRoleMenus(@RequestBody List<SysRoleMenu> records) {
        try {
            sysRoleService.saveRoleMenus(records);
        } catch (ServerException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

}