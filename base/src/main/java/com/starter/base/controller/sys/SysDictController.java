package com.starter.base.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.starter.base.constant.SysConstants;
import com.starter.base.controller.dto.SysDictDto;
import com.starter.base.model.sys.SysDict;
import com.starter.base.service.sys.SysDictService;
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
@Api(tags = "字典管理")
@RestController
@RequiresRoles(SysConstants.ADMIN)
@RequestMapping("/sys/dict")
public class SysDictController {
    @Autowired
    private SysDictService sysDictService;

    @RequiresPermissions(logical = Logical.AND, value = {"sys:dict:add"})
    @PostMapping(value = "/add")
    @ApiOperation(value = "添加字典", notes = "返回字典信息")
    public SysDict add(@ApiIgnore TokenUser tokenUser,
                       @ApiParam(value = "字典信息", required = true) @RequestBody @Validated SysDictDto record) {
        SysDict sysDict = new SysDict();
        BeanUtils.copyProperties(record, sysDict);
        sysDict.setCreateBy(tokenUser.getUsername());
        sysDict.setCreateTime(LocalDateTime.now());
        try {
            sysDictService.save(sysDict);
            return sysDict;
        } catch (DuplicateKeyException e) {
            throw ServerException.Found;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @RequiresPermissions(logical = Logical.AND, value = {"sys:dict:edit"})
    @PutMapping(value = "/{id}")
    @ApiOperation(value = "修改字典项", notes = "返回字典信息")
    public SysDict edit(@ApiIgnore TokenUser tokenUser,
                        @ApiParam(value = "字典id", required = true) @PathVariable Long id,
                        @ApiParam(value = "字典信息", required = true) @RequestBody @Validated SysDictDto record) {
        SysDict sysDict = sysDictService.getById(id);
        if (null == sysDict) {
            throw ServerException.NotFound;
        }
        BeanUtils.copyProperties(record, sysDict);
        sysDict.setLastUpdateBy(tokenUser.getUsername());
        sysDict.setLastUpdateTime(LocalDateTime.now());
        try {
            sysDictService.updateById(sysDict);
            return sysDict;
        } catch (DuplicateKeyException e) {
            throw ServerException.Found;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "删除字典项", notes = "返回void")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:dict:delete"})
    public void delete(@ApiParam(value = "删除id", required = true) @PathVariable Long id) {
        try {
            sysDictService.removeById(id);
        } catch (Exception e) {
            log.error(e.toString());
            throw ServerException.ServerError;
        }
    }

    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "批量删除字典项", notes = "返回void")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:dict:delete"})
    public void delete(@ApiParam(value = "字典id列表", required = true) @RequestBody List<Long> records) {
        try {
            sysDictService.removeByIds(records);
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @PostMapping(value = "/list")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:dict:view"})
    @ApiOperation(value = "字典列表", notes = "返回字典列表")
    public PageResult list(@ApiParam(value = "列表参数请求，params{value,label}") @RequestBody PageRequest pageRequest) {
        try {
            QueryWrapper<SysDict> queryWrapper = new QueryWrapper<>();
            Object value = pageRequest.getParam("value");
            if (!ObjectUtils.isEmpty(value)) {
                queryWrapper.like("value", value);
            }
            Object label = pageRequest.getParam("label");
            if (!ObjectUtils.isEmpty(label)) {
                queryWrapper.like("label", label);
            }
            return PageHelper.findPage(pageRequest, sysDictService, queryWrapper);
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

}
