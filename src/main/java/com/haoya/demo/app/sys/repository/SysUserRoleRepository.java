package com.haoya.demo.app.sys.repository;

import com.haoya.demo.app.sys.entity.SysUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;

public interface SysUserRoleRepository extends JpaRepository<SysUserRole, BigInteger>, SysUserRoleExtRepository {
    List<SysUserRole> findByUserId(BigInteger userId);
}
