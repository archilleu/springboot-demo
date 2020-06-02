package com.haoya.demo.app.service.sys;

import com.haoya.demo.app.model.sys.SysUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;

public interface SysUserRoleRepository extends JpaRepository<SysUserRole, BigInteger>, SysUserRoleExtRepository {
    List<SysUserRole> findByUserId(BigInteger userId);
}
