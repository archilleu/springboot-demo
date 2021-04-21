package com.hoya.admin.controller.sys;

import com.hoya.admin.annotation.OperatorLogger;
import com.hoya.admin.model.sys.SysConfig;
import com.hoya.admin.server.sys.SysConfigService;
import com.hoya.core.exception.ServerExceptionFound;
import com.hoya.core.exception.ServerExceptionServerError;
import com.hoya.core.page.PageRequest;
import com.hoya.core.page.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/sys/config")
public class SysConfigController {

    @Autowired
    private SysConfigService sysConfigService;

    @PreAuthorize("hasAuthority('sys:config:add') AND hasAuthority('sys:config:edit')")
    @PostMapping(value = "/save")
    public SysConfig save(@RequestBody @Validated SysConfig record) {
        try {
            sysConfigService.save(record);
            return record;
        } catch (ServerExceptionFound e) {
            throw e;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @PreAuthorize("hasAuthority('sys:config:delete')")
    @PostMapping(value = "/delete")
    public void delete(@RequestBody List<SysConfig> records) {
        try {
            sysConfigService.delete(records);
            return;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @OperatorLogger("获取配置列表")
    @PreAuthorize("hasAuthority('sys:config:view')")
    @PostMapping(value = "/findPage")
    public PageResult findPage(@RequestBody PageRequest pageRequest) {
        try {
            return sysConfigService.findPage(pageRequest);
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

}
