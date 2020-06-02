package com.haoya.demo.app.service.sys;

import com.haoya.demo.app.model.sys.SysRole;

import java.math.BigInteger;
import java.util.List;

public interface SysUserRoleExtRepository {
    void modifyUserRole(BigInteger userId, List<SysRole> roles);
}
