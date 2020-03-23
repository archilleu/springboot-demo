package com.haoya.demo.app.api.v1.admin.sys;

import com.haoya.demo.app.exception.AppException;
import com.haoya.demo.app.exception.AppExceptionFound;
import com.haoya.demo.app.sys.entity.SysRole;
import com.haoya.demo.app.sys.repository.SysRoleRepository;
import com.haoya.demo.common.utils.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.sql.Timestamp;

@RestController
@RequestMapping("/api/v1/admin/sys/role")
public class AdminSysRoleAPIController {

    @GetMapping("/list.json")
    public PageVO<SysRole> list(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) String roleName,
            @RequestParam(required = false) String remark) {

        if (null == page) {
            page = 0;
            limit = Integer.MAX_VALUE;
        } else {
            --page;   //page从1开始，数据库从0开始
        }

        try {
            PageRequest pageable = PageRequest.of(page, limit);
            if (StringUtils.isEmpty(roleName) && StringUtils.isEmpty(remark)) {
                return new PageVO<>(sysRoleRepository.findAll(pageable));
            } else {
                if (StringUtils.isEmpty(roleName)) {
                    roleName = "%";
                }
                if (StringUtils.isEmpty(remark)) {
                    remark = "%";
                }

                return new PageVO<>(sysRoleRepository.findByRoleNameLikeAndRemarkLike(roleName, remark, pageable));
            }
        } catch (Exception e) {
            throw AppException.ServerError;
        }
    }

    @PostMapping("/add.json")
    public SysRole add(@RequestBody SysRole sysRole) {
        sysRole.setCreateTime(new Timestamp(System.currentTimeMillis()));
        try {
            return sysRoleRepository.save(sysRole);
        } catch (DataIntegrityViolationException e) {
            throw new AppExceptionFound("角色已存在");
        } catch (Exception e) {
            throw AppException.ServerError;
        }
    }

    @PostMapping("/modify")
    public void modify(@RequestBody SysRole sysRole) {
        try {
            SysRole old = sysRoleRepository.findById(sysRole.getRoleId()).get();
            sysRole.setCreateTime(old.getCreateTime());
            sysRoleRepository.save(sysRole);
        } catch (DataIntegrityViolationException e) {
            throw new AppExceptionFound("角色已存在");
        } catch (Exception e) {
            throw AppException.ServerError;
        }
    }

    @GetMapping("/{id}.json")
    public SysRole get(@PathVariable(name = "id") BigInteger id) {
        try {
            return sysRoleRepository.findById(id).get();
        } catch (Exception e) {
            throw AppException.NotFound;
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") BigInteger id) {
        try {
            sysRoleRepository.deleteById(id);
        } catch (Exception e) {
            throw AppException.NotFound;
        }
    }

    @Autowired
    private SysRoleRepository sysRoleRepository;
}
