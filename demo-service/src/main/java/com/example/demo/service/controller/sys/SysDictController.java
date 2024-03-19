package com.example.demo.service.controller.sys;

import com.example.auth.vo.TokenUser;
import com.example.common.base.annotation.Log;
import com.example.common.base.vo.PageRequest;
import com.example.common.base.vo.PageResult;
import com.example.demo.service.constant.SysConstants;
import com.example.demo.service.model.entity.SysDict;
import com.example.demo.service.model.vo.input.SysDictInputVo;
import com.example.demo.service.model.vo.query.SysDictQueryVo;
import com.example.demo.service.service.ISysDictService;
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
 * 字典表 前端控制器
 * </p>
 *
 * @author cjy
 * @since 2023-07-13
 */
@Slf4j
@RestController
@Api(tags = "管理接口：字典管理接口")
@RequiresRoles(SysConstants.ADMIN)
@RequestMapping("/sys/dict")
public class SysDictController {

    @Autowired
    private ISysDictService sysDictService;

    @Log
    @RequiresPermissions(logical = Logical.AND, value = {"sys:dict:add"})
    @PostMapping(value = "/add")
    @ApiOperation(value = "添加字典", notes = "返回字典信息")
    public SysDict add(@ApiIgnore TokenUser tokenUser, @RequestBody @Validated SysDictInputVo record) {
        return sysDictService.add(tokenUser, record);
    }

    @Log
    @RequiresPermissions(logical = Logical.AND, value = {"sys:dict:edit"})
    @PutMapping(value = "/{id}")
    @ApiOperation(value = "修改字典项", notes = "返回字典信息")
    public SysDict edit(@ApiIgnore TokenUser tokenUser, @ApiParam(value = "字典id", required = true) @PathVariable Long id, @RequestBody @Validated SysDictInputVo record) {
        return sysDictService.edit(tokenUser, id, record);
    }

    @Log
    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "删除字典项", notes = "返回void")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:dict:delete"})
    public void delete(@ApiIgnore TokenUser tokenUser, @ApiParam(value = "删除id", required = true) @PathVariable Long id) {
        sysDictService.delete(tokenUser, id);
    }

    @Log
    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "批量删除字典项", notes = "返回void")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:dict:delete"})
    public void delete(@ApiIgnore TokenUser tokenUser, @ApiParam(value = "字典id列表", required = true) @RequestBody List<Long> records) {
        sysDictService.delete(tokenUser, records);
    }

    @Log
    @PostMapping(value = "/list")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:dict:view"})
    @ApiOperation(value = "字典列表", notes = "返回字典列表")
    public PageResult<SysDict> list(@ApiIgnore TokenUser tokenUser,
                                    @RequestBody @Validated PageRequest<SysDictQueryVo> pageRequest) {
        return PageResult.newInstance(sysDictService.findPage(tokenUser, pageRequest));
    }

}
