package com.hoya.admin.controller.sys;

import com.hoya.admin.model.sys.SysDept;
import com.hoya.admin.server.sys.SysDeptService;
import com.hoya.core.exception.ServerExceptionFound;
import com.hoya.core.exception.ServerExceptionNotFound;
import com.hoya.core.exception.ServerExceptionServerError;
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
@RequestMapping("/sys/dept")
public class SysDeptController {

    @Autowired
    private SysDeptService sysDeptService;

    @PreAuthorize("hasAuthority('sys:dept:add') AND hasAuthority('sys:dept:edit')")
    @PostMapping(value = "/save")
    public SysDept save(@RequestBody @Validated SysDept record, BindingResult bindingResult) {
        RequestParametersCheck.check(bindingResult);

        try {
            sysDeptService.save(record);
            return record;
        } catch (DuplicateKeyException e) {
            throw new ServerExceptionFound("部门已经存在");
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @PreAuthorize("hasAuthority('sys:dept:delete')")
    @PostMapping(value = "/delete")
    public void delete(@RequestBody List<SysDept> records) {
        try {
            sysDeptService.delete(records);
            return;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @PreAuthorize("hasAuthority('sys:dept:view')")
    @GetMapping(value = "/findByParentId")
    public List<SysDept> findTree(@RequestParam(required = false) Long parentId) {
        try {
            SysDept parent = null;
            if (null != parentId) {
                parent = sysDeptService.findById(parentId);
                if (null == parent)
                    throw new ServerExceptionNotFound("机构不存在");
            }

            List<SysDept> children = sysDeptService.findByParentId(parentId);
            return children;
        } catch (ServerExceptionNotFound e) {
            throw e;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

    @PreAuthorize("hasAuthority('sys:dept:view')")
    @GetMapping(value = "/getTree")
    public List<SysDept> findTreeAll() {
        try {
            return sysDeptService.getTree();
        } catch (Exception e) {
            log.error(e.toString());
            throw new ServerExceptionServerError("内部错误");
        }
    }

}
