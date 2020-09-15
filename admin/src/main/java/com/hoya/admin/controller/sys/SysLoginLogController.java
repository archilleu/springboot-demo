package com.hoya.admin.controller.sys;

import com.hoya.admin.model.sys.SysLoginLog;
import com.hoya.admin.server.sys.SysLoginLogService;
import com.hoya.core.exception.AppExceptionServerError;
import com.hoya.core.exception.HttpResult;
import com.hoya.core.page.PageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sys/login-log")
public class SysLoginLogController {

    private Logger logger = LoggerFactory.getLogger(SysLoginLogController.class);

    @Autowired
    private SysLoginLogService sysLoginLogService;

    @PreAuthorize("hasAuthority('sys:log:view')")
    @PostMapping(value = "/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        try {
            return new HttpResult(sysLoginLogService.findPage(pageRequest));
        } catch (Exception e) {
            logger.error(e.toString());
            throw new AppExceptionServerError("内部错误");
        }
    }

    @PreAuthorize("hasAuthority('sys:log:delete')")
    @PostMapping(value = "/delete")
    public HttpResult delete(@RequestBody List<SysLoginLog> records) {
        try {
            sysLoginLogService.delete(records);
            return HttpResult.OK;
        } catch (Exception e) {
            logger.error(e.toString());
            throw new AppExceptionServerError("内部错误");
        }
    }
}
