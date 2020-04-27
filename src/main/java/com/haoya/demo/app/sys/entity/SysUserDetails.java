package com.haoya.demo.app.sys.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class SysUserDetails implements UserDetails {

    public SysUserDetails(SysUser sysUser, List<SysRole> sysRoles, SysDept sysDept) {
        this.sysUser = sysUser;
        this.sysRoles = sysRoles;
        this.sysDept = sysDept;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sysUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if(sysUser.getStatus() == STATUS_DISABLE)
            return false;

        return true;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public List<SysRole> getSysRoles() {
        return sysRoles;
    }

    public SysDept getSysDept() {
        return sysDept;
    }

    public static final short STATUS_ENABLE = 1;
    public static final short STATUS_DISABLE = 0;

    private SysUser sysUser;
    private List<SysRole> sysRoles;
    private SysDept sysDept;
}
