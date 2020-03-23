package com.haoya.demo.app.sys.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class SysUserDetails implements UserDetails {

    public SysUserDetails(SysUser sysUser) {
        this.sysUser = sysUser;
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

    public static final short STATUS_ENABLE = 1;
    public static final short STATUS_DISABLE = 0;

    private SysUser sysUser;
}
