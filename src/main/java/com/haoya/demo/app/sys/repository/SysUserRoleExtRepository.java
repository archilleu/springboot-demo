package com.haoya.demo.app.sys.repository;

import com.haoya.demo.app.sys.entity.SysRole;

import java.math.BigInteger;
import java.util.List;

public interface SysUserRoleExtRepository {
    void modifyUserRole(BigInteger userId, List<SysRole> roles);
}
