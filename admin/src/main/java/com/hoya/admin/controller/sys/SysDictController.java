package com.hoya.admin.controller.sys;

import com.hoya.admin.model.sys.SysDict;
import com.hoya.admin.server.sys.SysDictService;
import com.hoya.core.exception.*;
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
@RequestMapping("/sys/dict")
public class SysDictController {

    private Logger logger = LoggerFactory.getLogger(SysDictController.class);

    @Autowired
    private SysDictService sysDictService;

    @PreAuthorize("hasAuthority('sys:dict:add') AND hasAuthority('sys:dict:edit')")
    @PostMapping(value = "/save")
    public HttpResult save(@RequestBody @Validated SysDict record, BindingResult bindingResult) {
        RequestParametersCheck.check(bindingResult);

        try {
            sysDictService.save(record);
            return HttpResult.OK;
        } catch (DuplicateKeyException e) {
            throw new AppExceptionAreadyExists("字典值已经存在");
        } catch (Exception e) {
            logger.error(e.toString());
            throw new AppExceptionServerError("内部错误");
        }
    }

    @PreAuthorize("hasAuthority('sys:dict:delete')")
    @PostMapping(value = "/delete")
    public HttpResult delete(@RequestBody List<SysDict> records) {
        try {
            sysDictService.delete(records);
            return HttpResult.OK;
        } catch (Exception e) {
            logger.error(e.toString());
            throw new AppExceptionServerError("内部错误");
        }
    }

    @PreAuthorize("hasAuthority('sys:dict:view')")
    @PostMapping(value = "/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        try {
            return new HttpResult(sysDictService.findPage(pageRequest));
        } catch (Exception e) {
            logger.error(e.toString());
            throw new AppExceptionServerError("内部错误");
        }
    }

}
