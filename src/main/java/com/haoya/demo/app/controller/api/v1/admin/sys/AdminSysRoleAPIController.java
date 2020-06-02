package com.haoya.demo.app.controller.api.v1.admin.sys;

import com.haoya.demo.app.exception.AppException;
import com.haoya.demo.app.exception.AppExceptionFound;
import com.haoya.demo.app.model.sys.SysRole;
import com.haoya.demo.app.service.sys.SysRoleRepository;
import com.haoya.demo.app.common.utils.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin/sys/role")
public class AdminSysRoleAPIController {

    /**
     * 获取角色表格
     * @param page 分页从1开始
     * @param limit 每页条数
     * @param roleName 搜索条件角色名称，支持SQL模糊匹配
     * @param remark 搜索条件备注名称，支持SQL模糊匹配
     * @return
     */
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
        Optional<SysRole> sysRoleOptional = sysRoleRepository.findById(id);
        if(!sysRoleOptional.isPresent()) {
            throw AppException.NotFound;
        }

        return sysRoleOptional.get();
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
