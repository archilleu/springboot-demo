package com.haoya.demo.app.api.v1.admin.sys;

import com.haoya.demo.app.exception.AppException;
import com.haoya.demo.app.sys.entity.SysMenu;
import com.haoya.demo.app.sys.repository.SysMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/sys/menu")
public class AdminSysMenuAPIController {

    @GetMapping("/list.json")
    public List<SysMenu> list(@RequestParam(required=false)String name) {
        if(StringUtils.isEmpty(name))
            return sysMenuRepository.findAll();

        return sysMenuRepository.findByNameLike(name);
    }

    @PostMapping("/add.json")
    public SysMenu add(@RequestBody SysMenu sysMenu) {
        return sysMenuRepository.save(sysMenu);
    }

    @PostMapping("/modify")
    public void modify(@RequestBody SysMenu sysMenu) {
        sysMenuRepository.save(sysMenu);
    }

    @GetMapping("/{id}.json")
    public SysMenu get(@PathVariable(name="id") BigInteger id) {
        try {
            return sysMenuRepository.findById(id).get();
        } catch (Exception e) {
            throw AppException.NotFound;
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name="id") BigInteger id) {
        sysMenuRepository.deleteMenuAndChild(id);
    }

    @Autowired
    private SysMenuRepository sysMenuRepository;
}