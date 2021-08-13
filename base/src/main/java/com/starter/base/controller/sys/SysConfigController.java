package com.starter.base.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.starter.base.constant.SysConstants;
import com.starter.base.controller.dto.SysConfigDto;
import com.starter.base.model.sys.SysConfig;
import com.starter.base.service.sys.SysConfigService;
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
@Api(tags = "配置管理")
@RestController
@RequiresRoles(SysConstants.ADMIN)
@RequestMapping("/sys/config")
public class SysConfigController {

    @Autowired
    private SysConfigService sysConfigService;

    @RequiresPermissions(logical = Logical.AND, value = {"sys:config:add"})
    @PostMapping(value = "/add")
    @ApiOperation(value = "添加配置项", notes = "返回配置项信息")
    public SysConfig add(@ApiIgnore TokenUser tokenUser,
                         @ApiParam(value = "配置信息", required = true) @RequestBody @Validated SysConfigDto record) {
        SysConfig sysConfig = new SysConfig();
        BeanUtils.copyProperties(record, sysConfig);
        sysConfig.setCreateBy(tokenUser.getUsername());
        sysConfig.setCreateTime(LocalDateTime.now());
        try {
            sysConfigService.save(sysConfig);
            return sysConfig;
        } catch (DuplicateKeyException e) {
            throw ServerException.Found;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @RequiresPermissions(logical = Logical.AND, value = {"sys:config:edit"})
    @PutMapping(value = "/{id}")
    @ApiOperation(value = "修改配置项", notes = "返回修改信息")
    public SysConfig edit(@ApiIgnore TokenUser tokenUser,
                          @ApiParam(value = "配置id", required = true) @PathVariable Long id,
                          @ApiParam(value = "配置信息", required = true) @RequestBody @Validated SysConfigDto record) {
        SysConfig sysConfig = sysConfigService.getById(id);
        if (null == sysConfig) {
            throw ServerException.NotFound;
        }
        BeanUtils.copyProperties(record, sysConfig);
        sysConfig.setLastUpdateBy(tokenUser.getUsername());
        sysConfig.setLastUpdateTime(LocalDateTime.now());
        try {
            sysConfigService.updateById(sysConfig);
            return sysConfig;
        } catch (DuplicateKeyException e) {
            throw ServerException.Found;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "删除配置项", notes = "返回void")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:config:delete"})
    public void delete(@ApiParam(value = "删除id", required = true) @PathVariable Long id) {
        try {
            sysConfigService.removeById(id);
        } catch (Exception e) {
            log.error(e.toString());
            throw ServerException.ServerError;
        }
    }

    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "批量删除配置项", notes = "返回void")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:config:delete"})
    public void delete(@ApiParam(value = "配置id列表", required = true) @RequestBody List<Long> records) {
        try {
            sysConfigService.removeByIds(records);
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @PostMapping(value = "/list")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:config:view"})
    @ApiOperation(value = "配置列表", notes = "返回配置列表")
    public PageResult list(@ApiParam(value = "列表参数请求，params{value,label}") @RequestBody PageRequest pageRequest) {
        try {
            QueryWrapper<SysConfig> queryWrapper = new QueryWrapper<>();
            Object value = pageRequest.getParam("value");
            if (!ObjectUtils.isEmpty(value)) {
                queryWrapper.like("value", value);
            }
            Object label = pageRequest.getParam("label");
            if (!ObjectUtils.isEmpty(label)) {
                queryWrapper.like("label", label);
            }
            return PageHelper.findPage(pageRequest, sysConfigService, queryWrapper);
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

}
