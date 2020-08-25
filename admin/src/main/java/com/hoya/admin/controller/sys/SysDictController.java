package com.hoya.admin.controller.sys;

import com.hoya.admin.model.sys.SysDict;
import com.hoya.admin.server.sys.SysDictService;
import com.hoya.core.page.PageRequest;
import com.hoya.core.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("dict")
public class SysDictController {

    @Autowired
    private SysDictService sysDictService;

    @PreAuthorize("hasAuthority('sys:dict:add') AND hasAuthority('sys:dict:edit')")
    @PostMapping(value = "/save")
    public void save(@RequestBody SysDict record) {
        int res = sysDictService.save(record);
    }

    @PreAuthorize("hasAuthority('sys:dict:delete')")
    @PostMapping(value = "/delete")
    public void delete(@RequestBody List<SysDict> records) {
        int res = sysDictService.delete(records);
    }

    @PreAuthorize("hasAuthority('sys:dict:view1')")
    @PostMapping(value = "/findPage")
    public PageResult findPage(@RequestBody PageRequest pageRequest) {
        PageResult res = sysDictService.findPage(pageRequest);
        return res;
    }

}
