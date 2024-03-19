package com.example.demo.service.controller.sys;

import com.example.auth.vo.TokenUser;
import com.example.common.base.annotation.Log;
import com.example.common.base.vo.PageRequest;
import com.example.common.base.vo.PageResult;
import com.example.demo.service.constant.SysConstants;
import com.example.demo.service.model.entity.SysConfig;
import com.example.demo.service.model.vo.input.SysConfigInputVo;
import com.example.demo.service.model.vo.query.SysConfigQueryVo;
import com.example.demo.service.service.ISysConfigService;
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
 * 系统配置表 前端控制器
 * </p>
 *
 * @author cjy
 * @since 2023-07-13
 */
@Slf4j
@RestController
@RequiresRoles(SysConstants.ADMIN)
@Api(tags = "管理接口：系统配置管理接口")
@RequestMapping("/sys/config")
public class SysConfigController {
    @Autowired
    private ISysConfigService sysConfigService;

    @Log
    @RequiresPermissions(logical = Logical.AND, value = {"sys:config:add"})
    @PostMapping(value = "/add")
    @ApiOperation(value = "添加配置项", notes = "返回配置项信息")
    public SysConfig add(@ApiIgnore TokenUser tokenUser, @RequestBody @Validated SysConfigInputVo record) {
        return sysConfigService.add(tokenUser, record);
    }

    @Log
    @RequiresPermissions(logical = Logical.AND, value = {"sys:config:edit"})
    @PutMapping(value = "/{id}")
    @ApiOperation(value = "修改配置项", notes = "返回修改信息")
    public SysConfig edit(@ApiIgnore TokenUser tokenUser,
                          @ApiParam(value = "配置项id", required = true, example = "1000") @PathVariable Long id,
                          @RequestBody @Validated SysConfigInputVo record) {
        return sysConfigService.edit(tokenUser, id, record);
    }

    @Log
    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "删除配置项", notes = "返回void")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:config:delete"})
    public void delete(@ApiIgnore TokenUser tokenUser,
                       @ApiParam(value = "删除id", required = true, example = "1000") @PathVariable Long id) {
        sysConfigService.delete(tokenUser, id);
    }

    @Log
    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "批量删除配置项", notes = "返回void")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:config:delete"})
    public void delete(@ApiIgnore TokenUser tokenUser, @RequestBody List<Long> records) {
        sysConfigService.delete(tokenUser, records);
    }

    @Log
    @PostMapping("list")
    @RequiresPermissions(logical = Logical.AND, value = {"sys:config:view"})
    @ApiOperation(value = "配置列表", notes = "返回配置列表")
    public PageResult<SysConfig> list(@ApiIgnore TokenUser tokenUser,
                                      @RequestBody @Validated PageRequest<SysConfigQueryVo> pageRequest) {
        return PageResult.newInstance(sysConfigService.findPage(tokenUser, pageRequest));
    }
}
