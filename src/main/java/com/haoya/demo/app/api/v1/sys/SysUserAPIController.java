package com.haoya.demo.app.api.v1.sys;

import com.haoya.demo.app.exception.AppException;
import com.haoya.demo.app.sys.entity.SysUser;
import com.haoya.demo.app.sys.entity.SysUserDetails;
import com.haoya.demo.app.sys.repository.SysUserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sys/user")
public class SysUserAPIController {

    @PostMapping("/password")
    public void password(@RequestParam(required = true)String oldPassword, @RequestParam(required = true)String newPassword) {
        String oldCrypt = bCryptPasswordEncoder.encode(oldPassword);
        SysUserDetails userDetails = (SysUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUser sysUser = userDetails.getSysUser();
        if(sysUser.getPassword().equals(oldCrypt))
            throw AppException.Forbidden;

        sysUser.setPassword(bCryptPasswordEncoder.encode(newPassword));
        sysUserRepository.save(sysUser);

        return;
    }

    @PostMapping("/modify")
    public void modify(@RequestBody SysUser update) {
        SysUserDetails userDetails = (SysUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        SysUser sysUser = userDetails.getSysUser();
        BeanUtils.copyProperties(sysUser, update, "nickname", "email", "mobile");
        sysUserRepository.save(update);
    }

    @GetMapping("/info")
    public SysUserDetails info() {
        SysUserDetails userDetails = (SysUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails;
    }


    @Autowired
    private SysUserRepository sysUserRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
}
