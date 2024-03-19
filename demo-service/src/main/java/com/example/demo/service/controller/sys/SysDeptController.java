package com.example.demo.service.controller.sys;

import com.example.auth.vo.TokenUser;
import com.example.common.base.annotation.Log;
import com.example.demo.service.constant.SysConstants;
import com.example.demo.service.model.vo.SysDeptVo;
import com.example.demo.service.model.vo.input.SysDeptInputVo;
import com.example.demo.service.service.ISysDeptService;
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
 * 机构管理 前端控制器
 * </p>
 *
 * @author cjy
 * @since 2023-07-13
 */
@Slf4j
@RestController
@RequiresRoles(SysConstants.ADMIN)
@Api(tags = "管理接口：部门管理接口")
@RequestMapping("/sys/dept")
public class SysDeptController {

    @Autowired
    private ISysDeptService sysDeptService;

    @Log
    @RequiresPermissions(logical = Logical.AND, value = {"sys:dept:add"})
    @PostMapping(value = "/add")
    @ApiOperation(value = "添加部门", notes = "返回部门信息")
    public SysDeptVo add(@ApiIgnore TokenUser tokenUser, @RequestBody @Validated SysDeptInputVo record) {
        return sysDeptService.add(tokenUser, record);
    }

    @Log
    @RequiresPermissions(logical = Logical.AND, value = {"sys:dept:edit"})
    @PutMapping(value = "/{id}")
    @ApiOperation(value = "修改部门", notes = "返回部门信息")
    public SysDeptVo edit(@ApiIgnore TokenUser tokenUser,
                          @ApiParam(value = "部门id", required = true, example = "1000") @PathVariable Long id,
                          @RequestBody @Validated SysDeptInputVo record) {
        return sysDeptService.edit(tokenUser, id, record);
    }

    @Log
    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "删除部门", notes = "返回void")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:dept:delete"})
    public void delete(@ApiIgnore TokenUser tokenUser,
                       @ApiParam(value = "部门id", required = true, example = "1000") @PathVariable Long id) {
        sysDeptService.delete(tokenUser, id);
    }

    @Log
    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "批量删除部门", notes = "返回void")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:dept:delete"})
    public void delete(@ApiIgnore TokenUser tokenUser,
                       @ApiParam(value = "部门id列表", required = true) @RequestBody List<Long> records) {
        sysDeptService.delete(tokenUser, records);
    }

    @Log
    @GetMapping(value = "/{id}/children")
    @ApiOperation(value = "根据部门id获取子部门", notes = "返回子部门列表")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:dept:view"})
    public List<SysDeptVo> children(@ApiIgnore TokenUser tokenUser,
                                    @ApiParam(value = "部门id", required = true, example = "0") @PathVariable Long id) {
        return sysDeptService.list(tokenUser, id);
    }

    @Log
    @GetMapping(value = "/tree")
    @ApiOperation(value = "获取整颗目录树", notes = "返回部门目录树")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:dept:view"})
    public List<SysDeptVo> getTree(@ApiIgnore TokenUser tokenUser) {
        return sysDeptService.tree(tokenUser);
    }
}
