package com.hoya.admin.controller.sys;

import com.hoya.admin.server.sys.SysUserService;
import com.hoya.core.page.PageRequest;
import com.hoya.core.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class SysUserController {

    @PostMapping(value = "/findPage")
    public PageResult findPage(@RequestBody PageRequest pageRequest) {
        return sysUserService.findPage(pageRequest);
    }

    @Autowired
    private SysUserService sysUserService;
}
