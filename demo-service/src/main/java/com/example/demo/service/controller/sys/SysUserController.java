package com.example.demo.service.controller.sys;

import com.example.auth.vo.TokenUser;
import com.example.common.base.vo.PageRequest;
import com.example.common.base.vo.PageResult;
import com.example.demo.service.constant.SysConstants;
import com.example.demo.service.model.vo.SysUserVo;
import com.example.demo.service.model.vo.input.SysUserInputVo;
import com.example.demo.service.model.vo.input.SysUserRoleInputVo;
import com.example.demo.service.model.vo.input.UserPasswordInputVo;
import com.example.demo.service.model.vo.query.SysUserQueryVo;
import com.example.demo.service.service.ISysDeptService;
import com.example.demo.service.service.ISysUserService;
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
 * 用户管理 前端控制器
 * </p>
 *
 * @author cjy
 * @since 2023-07-13
 */
@Slf4j
@Api(tags = "管理接口：用户管理接口")
@RestController
@RequiresRoles(SysConstants.ADMIN)
@RequestMapping("/sys/user")
public class SysUserController {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysDeptService sysDeptService;

    @PostMapping("/add")
    @ApiOperation(value = "添加用户", notes = "返回用户信息")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:user:add"})
    public SysUserVo add(@ApiIgnore TokenUser tokenUser,
                         @RequestBody @Validated SysUserInputVo record) {
        return sysUserService.add(tokenUser, record);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "修改用户", notes = "返回用户信息")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:user:edit"})
    public SysUserVo edit(@ApiIgnore TokenUser tokenUser,
                          @ApiParam(value = "用户id", required = true) @PathVariable Long id,
                          @RequestBody @Validated SysUserInputVo record) {
        return sysUserService.edit(tokenUser, id, record);
    }

    @PutMapping("/{id}/password")
    @ApiOperation(value = "修改用户密码", notes = "返回void")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:user:edit"})
    public void modifyPassword(@ApiIgnore TokenUser tokenUser,
                               @ApiParam(value = "用户id", required = true) @PathVariable Long id,
                               @RequestBody @Validated UserPasswordInputVo record) {
        sysUserService.modifyPassword(tokenUser, id, record);
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "删除用户", notes = "返回void")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:user:delete"})
    public void delete(@ApiIgnore TokenUser tokenUser,
                       @ApiParam(value = "用户id", required = true) @PathVariable Long id) {
        sysUserService.delete(tokenUser, id);
    }

    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "批量删除用户", notes = "返回void")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:user:delete"})
    public void delete(@ApiIgnore TokenUser tokenUser,
                       @ApiParam(value = "用户id列表", required = true) @RequestBody List<Long> records) {
        sysUserService.delete(tokenUser, records);
    }

    @PostMapping("/save-user-roles")
    @ApiOperation(value = "保存用户角色", notes = "返回void")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:user:edit"})
    public void saveUserRole(@ApiIgnore TokenUser tokenUser,
                             @RequestBody @Validated SysUserRoleInputVo record) {
        sysUserService.saveUserRoles(tokenUser, record);
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "获取用户列表", notes = "返回用户列表")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:user:view"})
    public PageResult<SysUserVo> findPage(@ApiIgnore TokenUser tokenUser,
                                          @RequestBody @Validated PageRequest<SysUserQueryVo> pageRequest) {
        return PageResult.newInstance(sysUserService.findPage(tokenUser, pageRequest));
    }
}
