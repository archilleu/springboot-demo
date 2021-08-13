package com.starter.base.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.starter.base.constant.SysConstants;
import com.starter.base.model.sys.SysLog;
import com.starter.base.service.sys.SysLogService;
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
@Api(tags = "日志管理")
@RestController
@RequiresRoles(SysConstants.ADMIN)
@RequestMapping("/sys/log")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "删除日志", notes = "返回void")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:log:delete"})
    public void delete(@ApiParam(value = "日志id", required = true) @PathVariable Long id) {
        try {
            sysLogService.removeById(id);
        } catch (Exception e) {
            log.error(e.toString());
            throw ServerException.ServerError;
        }
    }

    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "批量删除日志", notes = "返回void")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:log:delete"})
    public void delete(@ApiParam(value = "日志id列表", required = true) @RequestBody List<Long> records) {
        try {
            sysLogService.removeByIds(records);
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @PostMapping(value = "/list")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:log:view"})
    @ApiOperation(value = "日志列表", notes = "返回日志列表")
    public PageResult list(@ApiParam(value = "列表参数请求，params{userName,label}") @RequestBody PageRequest pageRequest) {
        try {
            QueryWrapper<SysLog> queryWrapper = new QueryWrapper<>();
            Object userName = pageRequest.getParam("userName");
            if (!ObjectUtils.isEmpty(userName)) {
                queryWrapper.like("user_name", userName);
            }
            return PageHelper.findPage(pageRequest, sysLogService, queryWrapper);
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }
}
