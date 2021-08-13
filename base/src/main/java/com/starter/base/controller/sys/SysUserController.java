package com.starter.base.controller.sys;

import com.starter.base.constant.SysConstants;
import com.starter.base.controller.dto.SysUserAddDto;
import com.starter.base.controller.dto.SysUserEditDto;
import com.starter.base.controller.dto.SysUserPasswordDto;
import com.starter.base.controller.vo.SysUserRolesVo;
import com.starter.base.controller.vo.SysUserVo;
import com.starter.base.model.sys.SysDept;
import com.starter.base.model.sys.SysUser;
import com.starter.base.service.sys.SysDeptService;
import com.starter.base.service.sys.SysUserService;
import com.starter.base.shiro.util.TokenUser;
import com.starter.base.util.PasswordUtils;
import com.starter.common.exception.ServerException;
import com.starter.common.exception.ServerExceptionForbidden;
import com.starter.common.exception.ServerExceptionServerError;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Api(tags = "用户管理")
@RestController
@RequestMapping("/sys/user")
@RequiresRoles(SysConstants.ADMIN)
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysDeptService sysDeptService;

    @PostMapping("/add")
    @ApiOperation(value = "添加用户", notes = "返回用户信息")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:user:add"})
    public SysUserVo add(@ApiIgnore TokenUser tokenUser,
                         @ApiParam(value = "用户信息", required = true) @RequestBody @Validated SysUserAddDto record) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(record, sysUser);

        String salt = PasswordUtils.getSalt();
        String password = PasswordUtils.encode(record.getPassword(), salt);
        sysUser.setSalt(salt);
        sysUser.setPassword(password);
        sysUser.setCreateBy(tokenUser.getUsername());
        sysUser.setCreateTime(LocalDateTime.now());
        try {
            sysUserService.save(sysUser);
            SysUserVo sysUserVo = new SysUserVo();
            BeanUtils.copyProperties(sysUser, sysUserVo);
            if (null != record.getDeptId()) {
                SysDept sysDept = sysDeptService.getById(record.getDeptId());
                sysUserVo.setDeptName(sysDept.getName());
            }
            sysUserVo.setRoles(new LinkedList<>());
            return sysUserVo;
        } catch (DuplicateKeyException e) {
            throw ServerException.Found;
        } catch (Exception e) {
            log.error(e.toString());
            throw ServerException.ServerError;
        }
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "修改用户", notes = "返回用户信息")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:user:edit"})
    public SysUserVo edit(@ApiIgnore TokenUser tokenUser,
                          @ApiParam(value = "用户id", required = true) @PathVariable Long id,
                          @ApiParam(value = "用户信息", required = true) @RequestBody @Validated SysUserEditDto record) {
        if (SysConstants.ADMIN_ID.equals(id)) {
            throw new ServerExceptionForbidden("不能修改超级管理员");
        }

        SysUser user = sysUserService.getById(id);
        if (null == user) {
            throw ServerException.NotFound;
        }
        BeanUtils.copyProperties(record, user);

        try {
            if (record.getPassword() != null) {
                String salt = PasswordUtils.getSalt();
                String password = PasswordUtils.encode(record.getPassword(), salt);
                user.setSalt(salt);
                user.setPassword(password);
            }
            user.setLastUpdateBy(tokenUser.getUsername());
            user.setLastUpdateTime(LocalDateTime.now());
            sysUserService.updateById(user);
            SysUserVo sysUserVo = new SysUserVo();
            BeanUtils.copyProperties(user, sysUserVo);
            if (null != user.getDeptId()) {
                SysDept sysDept = sysDeptService.getById(user.getDeptId());
                sysUserVo.setDeptName(sysDept.getName());
            }

            //TODO:role

            return sysUserVo;
        } catch (Exception e) {
            log.error(e.toString());
            throw ServerException.ServerError;
        }
    }

    @PutMapping("/{id}/password")
    @ApiOperation(value = "修改用户密码", notes = "返回void")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:user:edit"})
    public void modifyPassword(@ApiIgnore TokenUser tokenUser,
                               @ApiParam(value = "用户id", required = true) @PathVariable Long id,
                               @ApiParam(value = "用户密码", required = true) @RequestBody @Validated SysUserPasswordDto record) {
        if (SysConstants.ADMIN_ID.equals(id)) {
            throw new ServerExceptionForbidden("不能修改超级管理员");
        }

        SysUser user = sysUserService.getById(id);
        if (null == user) {
            throw ServerException.NotFound;
        }

        String salt = PasswordUtils.getSalt();
        String password = PasswordUtils.encode(record.getPassword(), salt);
        user.setSalt(salt);
        user.setPassword(password);
        user.setLastUpdateBy(tokenUser.getUsername());
        user.setLastUpdateTime(LocalDateTime.now());
        try {
            sysUserService.updateById(user);
        } catch (Exception e) {
            log.error(e.toString());
            throw ServerException.ServerError;
        }
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "删除用户", notes = "返回void")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:user:delete"})
    public void delete(@ApiParam(value = "用户id", required = true) @PathVariable Long id) {
        if (SysConstants.ADMIN_ID.equals(id)) {
            throw new ServerExceptionForbidden("不能删除超级管理员");
        }

        try {
            sysUserService.removeById(id);
        } catch (Exception e) {
            log.error(e.toString());
            throw ServerException.ServerError;
        }
    }

    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "批量删除用户", notes = "返回void")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:user:delete"})
    public void delete(@ApiParam(value = "用户id列表", required = true) @RequestBody List<Long> records) {
        for (Long record : records) {
            if (SysConstants.ADMIN_ID.equals(record))
                throw new ServerExceptionForbidden("不能删除超级管理员");
        }

        try {
            sysUserService.removeByIds(records);
        } catch (Exception e) {
            log.error(e.toString());
            throw ServerException.ServerError;
        }
    }

    @PostMapping("/save-user-roles")
    @ApiOperation(value = "保存用户角色", notes = "返回void")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:user:edit"})
    public void saveUserRole(@ApiParam(value = "用户角色列表") @RequestBody @Validated SysUserRolesVo sysUserRolesBean) {
        try {
            sysUserService.saveUserRoles(sysUserRolesBean);
            return;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "获取用户列表", notes = "返回用户列表")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:user:view"})
    public PageResult findPage(@ApiParam(value = "列表请求参数,params{name,email}") @RequestBody PageRequest pageRequest) {
        try {
            return sysUserService.findPage(pageRequest);
        } catch (Exception e) {
            log.error(e.toString());
            throw ServerException.ServerError;
        }
    }

}
