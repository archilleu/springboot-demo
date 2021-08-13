package com.starter.base.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.starter.base.constant.SysConstants;
import com.starter.base.controller.dto.SysDeptDto;
import com.starter.base.controller.vo.SysDeptVo;
import com.starter.base.model.sys.SysDept;
import com.starter.base.service.sys.SysDeptService;
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
@Api(tags = "部门管理")
@RestController
@RequiresRoles(SysConstants.ADMIN)
@RequestMapping("/sys/dept")
public class SysDeptController {

    @Autowired
    private SysDeptService sysDeptService;

    @RequiresPermissions(logical = Logical.AND, value = {"sys:dept:add"})
    @PostMapping(value = "/add")
    @ApiOperation(value = "添加部门", notes = "返回部门信息")
    public SysDeptVo add(@ApiIgnore TokenUser tokenUser,
                         @ApiParam(value = "部门信息", required = true) @RequestBody @Validated SysDeptDto record) {
        SysDept sysDept = new SysDept();
        BeanUtils.copyProperties(record, sysDept);
        if (sysDept.getParentId() == null) {
            sysDept.setParentId(0l);
        }
        sysDept.setCreateBy(tokenUser.getUsername());
        sysDept.setCreateTime(LocalDateTime.now());
        try {
            sysDeptService.save(sysDept);
            SysDeptVo sysDeptVo = new SysDeptVo();
            BeanUtils.copyProperties(sysDept, sysDeptVo);
            return sysDeptVo;
        } catch (DuplicateKeyException e) {
            throw ServerException.Found;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @RequiresPermissions(logical = Logical.AND, value = {"sys:dept:edit"})
    @PutMapping(value = "/{id}")
    @ApiOperation(value = "修改部门", notes = "返回部门信息")
    public SysDeptVo edit(@ApiIgnore TokenUser tokenUser,
                          @ApiParam(value = "部门id", required = true) @PathVariable Long id,
                          @ApiParam(value = "部门信息") @RequestBody @Validated SysDeptDto record) {
        SysDept sysDept = sysDeptService.getById(id);
        if (null == sysDept) {
            throw ServerException.NotFound;
        }
        BeanUtils.copyProperties(record, sysDept);
        sysDept.setLastUpdateBy(tokenUser.getUsername());
        sysDept.setLastUpdateTime(LocalDateTime.now());

        try {
            sysDeptService.updateById(sysDept);
            SysDeptVo sysDeptVo = new SysDeptVo();
            BeanUtils.copyProperties(sysDept, sysDeptVo);
            return sysDeptVo;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "删除部门", notes = "返回void")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:dept:delete"})
    public void delete(@ApiParam(value = "部门id", required = true) @PathVariable Long id) {
        try {
            sysDeptService.removeById(id);
        } catch (Exception e) {
            log.error(e.toString());
            throw ServerException.ServerError;
        }
    }

    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "批量删除部门", notes = "返回void")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:dept:delete"})
    public void delete(@ApiParam(value = "部门id列表", required = true) @RequestBody List<Long> records) {
        try {
            sysDeptService.removeByIds(records);
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @GetMapping(value = "/{id}/children")
    @ApiOperation(value = "根据部门id获取子部门", notes = "返回子部门列表")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:dept:view"})
    public List<SysDept> children(@PathVariable(required = true) Long id) {
        try {
            QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("parent_id", id);
            return sysDeptService.list(queryWrapper);
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @GetMapping(value = "/tree")
    @ApiOperation(value = "获取整颗目录树", notes = "返回部门目录树")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:dept:view"})
    public List<SysDeptVo> getTree() {
        try {
            return sysDeptService.getTree();
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

}
