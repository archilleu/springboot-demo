package com.hoya.admin.controller.sys;

import com.hoya.admin.model.sys.SysLog;
import com.hoya.admin.server.sys.SysLogService;
import com.hoya.core.exception.AppExceptionServerError;
import com.hoya.core.exception.HttpResult;
import com.hoya.core.page.PageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys/log")
public class SysLogController {

    private Logger logger = LoggerFactory.getLogger(SysLogController.class);

    @Autowired
    private SysLogService sysLogService;

    @PreAuthorize("hasAuthority('sys:log:view')")
    @PostMapping(value = "/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        try {
            return new HttpResult(sysLogService.findPage(pageRequest));
        } catch (Exception e) {
            logger.error(e.toString());
            throw new AppExceptionServerError("内部错误");
        }
    }

    @PreAuthorize("hasAuthority('sys:log:delete')")
    @PostMapping(value = "/delete")
    public HttpResult delete(@RequestBody List<SysLog> records) {
        try {
            sysLogService.delete(records);
            return HttpResult.OK;
        } catch (Exception e) {
            logger.error(e.toString());
            throw new AppExceptionServerError("内部错误");
        }
    }
}
