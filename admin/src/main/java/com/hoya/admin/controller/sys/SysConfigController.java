package com.hoya.admin.controller.sys;

import com.hoya.admin.model.sys.SysConfig;
import com.hoya.admin.server.sys.SysConfigService;
import com.hoya.core.exception.AppExceptionAreadyExists;
import com.hoya.core.exception.AppExceptionServerError;
import com.hoya.core.exception.HttpResult;
import com.hoya.core.page.PageRequest;
import com.hoya.core.utils.RequestParametersCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys/config")
public class SysConfigController {

    private Logger logger = LoggerFactory.getLogger(SysConfigController.class);

    @Autowired
    private SysConfigService sysConfigService;

    @PreAuthorize("hasAuthority('sys:config:add') AND hasAuthority('sys:config:edit')")
    @PostMapping(value = "/save")
    public HttpResult save(@RequestBody @Validated SysConfig record, BindingResult bindingResult) {
        RequestParametersCheck.check(bindingResult);

        try {
            sysConfigService.save(record);
            return HttpResult.OK;
        } catch (DuplicateKeyException e) {
            throw new AppExceptionAreadyExists("配置项已经存在");
        } catch (Exception e) {
            logger.error(e.toString());
            throw new AppExceptionServerError("内部错误");
        }
    }

    @PreAuthorize("hasAuthority('sys:config:delete')")
    @PostMapping(value = "/delete")
    public HttpResult delete(@RequestBody List<SysConfig> records) {
        try {
            sysConfigService.delete(records);
            return HttpResult.OK;
        } catch (Exception e) {
            logger.error(e.toString());
            throw new AppExceptionServerError("内部错误");
        }
    }

    @PreAuthorize("hasAuthority('sys:config:view')")
    @PostMapping(value = "/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        try {
            return new HttpResult(sysConfigService.findPage(pageRequest));
        } catch (Exception e) {
            logger.error(e.toString());
            throw new AppExceptionServerError("内部错误");
        }
    }

    @PreAuthorize("hasAuthority('sys:config:view')")
    @GetMapping(value = "/findByLable")
    public HttpResult findByLable(@RequestParam String lable) {
        try {
            return new HttpResult(sysConfigService.findByLable(lable));
        } catch (Exception e) {
            logger.error(e.toString());
            throw new AppExceptionServerError("内部错误");
        }
    }
}
