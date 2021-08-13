package com.starter.base.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.starter.base.constant.SysConstants;
import com.starter.base.model.sys.SysLoginLog;
import com.starter.base.service.sys.SysLoginLogService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = "用户登陆日志")
@RestController
@RequiresRoles(SysConstants.ADMIN)
@RequestMapping("/sys/login-log")
public class SysLoginLogController {

    @Autowired
    private SysLoginLogService sysLoginLogService;

    @PostMapping(value = "/list")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:loginlog:view"})
    @ApiOperation(value = "登陆日志菜单列表", notes = "返回用户登陆日志列表")
    public PageResult list(@ApiParam(value = "列表请求参数, params{userName}", required = true) @RequestBody PageRequest pageRequest) {
        try {
            QueryWrapper<SysLoginLog> queryWrapper = new QueryWrapper<>();
            Object userName = pageRequest.getParam("userName");
            if (!ObjectUtils.isEmpty(userName)) {
                queryWrapper.like("user_name", userName);
            }
            Object status = pageRequest.getParam("status");
            if (!ObjectUtils.isEmpty(status)) {
                queryWrapper.like("status", status);
            }
            return PageHelper.findPage(pageRequest, sysLoginLogService, queryWrapper);
        } catch (ServerException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "删除登陆日志", notes = "返回void")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:log:delete"})
    public void delete(@ApiParam(value = "日志id", required = true) @PathVariable Long id) {
        try {
            sysLoginLogService.removeById(id);
        } catch (Exception e) {
            log.error(e.toString());
            throw ServerException.ServerError;
        }
    }

    @DeleteMapping(value = "/delete")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:loginlog:delete"})
    @ApiOperation(value = "批量删除日志", notes = "返回void")
    public void delete(@ApiParam(value = "日志id列表", required = true) @RequestBody List<Long> records) {
        try {
            sysLoginLogService.removeByIds(records);
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }
}
