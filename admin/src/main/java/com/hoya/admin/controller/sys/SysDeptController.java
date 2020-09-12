package com.hoya.admin.controller.sys;

import com.hoya.admin.model.sys.SysDept;
import com.hoya.admin.server.sys.SysDeptService;
import com.hoya.core.exception.AppExceptionAreadyExists;
import com.hoya.core.exception.AppExceptionNotFound;
import com.hoya.core.exception.AppExceptionServerError;
import com.hoya.core.exception.HttpResult;
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
@RequestMapping("/sys/dept")
public class SysDeptController {

    private Logger logger = LoggerFactory.getLogger(SysDeptController.class);

    @Autowired
    private SysDeptService sysDeptService;

    @PreAuthorize("hasAuthority('sys:dept:add') AND hasAuthority('sys:dept:edit')")
    @PostMapping(value = "/save")
    public HttpResult save(@RequestBody @Validated SysDept record, BindingResult bindingResult) {
        RequestParametersCheck.check(bindingResult);

        try {
            sysDeptService.save(record);
            return new HttpResult(record);
        } catch (DuplicateKeyException e) {
            throw new AppExceptionAreadyExists("部门已经存在");
        } catch (Exception e) {
            logger.error(e.toString());
            throw new AppExceptionServerError("内部错误");
        }
    }

    @PreAuthorize("hasAuthority('sys:dept:delete')")
    @PostMapping(value = "/delete")
    public HttpResult delete(@RequestBody List<SysDept> records) {
        try {
            sysDeptService.delete(records);
            return HttpResult.OK;
        } catch (Exception e) {
            logger.error(e.toString());
            throw new AppExceptionServerError("内部错误");
        }
    }

    @PreAuthorize("hasAuthority('sys:dept:view')")
    @GetMapping(value = "/findTree")
    public HttpResult findTree(@RequestParam(required = false) Long parentId) {
        try {
            SysDept parent = null;
            if (null != parentId) {
                parent = sysDeptService.findById(parentId);
                if (null == parent)
                    throw new AppExceptionNotFound("机构不存在");
            }

            List<SysDept> children = sysDeptService.findTree(parentId);
            return new HttpResult(children);
        } catch (AppExceptionNotFound e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.toString());
            throw new AppExceptionServerError("内部错误");
        }
    }

    @PreAuthorize("hasAuthority('sys:dept:view')")
    @GetMapping(value = "/findTreeAll")
    public HttpResult findTreeAll() {
        try {
            return new HttpResult(sysDeptService.findTree());
        } catch (Exception e) {
            logger.error(e.toString());
            throw new AppExceptionServerError("内部错误");
        }
    }

}
