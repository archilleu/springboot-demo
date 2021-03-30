package com.hoya.admin.controller.sys;

import com.hoya.admin.model.sys.SysDict;
import com.hoya.admin.server.sys.SysDictService;
import com.hoya.core.exception.*;
import com.hoya.core.page.PageRequest;
import com.hoya.core.page.PageResult;
import com.hoya.core.utils.RequestParametersCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/sys/dict")
public class SysDictController {

    @Autowired
    private SysDictService sysDictService;

    @PreAuthorize("hasAuthority('sys:dict:add') AND hasAuthority('sys:dict:edit')")
    @PostMapping(value = "/save")
    public SysDict save(@RequestBody @Validated SysDict record, BindingResult bindingResult) {
        RequestParametersCheck.check(bindingResult);

        try {
            sysDictService.save(record);
            return record;
        } catch (DuplicateKeyException e) {
            throw new ServerExceptionFound("字典值已经存在");
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @PreAuthorize("hasAuthority('sys:dict:delete')")
    @PostMapping(value = "/delete")
    public void delete(@RequestBody List<SysDict> records) {
        try {
            sysDictService.delete(records);
            return;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @PreAuthorize("hasAuthority('sys:dict:view')")
    @PostMapping(value = "/findPage")
    public PageResult findPage(@RequestBody PageRequest pageRequest) {
        try {
            return sysDictService.findPage(pageRequest);
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

}
