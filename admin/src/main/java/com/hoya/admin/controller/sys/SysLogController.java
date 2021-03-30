package com.hoya.admin.controller.sys;

import com.hoya.admin.model.sys.SysLog;
import com.hoya.admin.server.sys.SysLogService;
import com.hoya.core.exception.ServerExceptionServerError;
import com.hoya.core.page.PageRequest;
import com.hoya.core.page.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/sys/log")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @PreAuthorize("hasAuthority('sys:log:view')")
    @PostMapping(value = "/findPage")
    public PageResult findPage(@RequestBody PageRequest pageRequest) {
        try {
            return sysLogService.findPage(pageRequest);
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @PreAuthorize("hasAuthority('sys:log:delete')")
    @PostMapping(value = "/delete")
    public void delete(@RequestBody List<SysLog> records) {
        try {
            sysLogService.delete(records);
            return;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }
}
