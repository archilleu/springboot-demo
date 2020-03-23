package com.haoya.demo.app.sys.repository;

import com.haoya.demo.app.sys.entity.SysRoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;

public interface SysRoleMenuRepository extends JpaRepository<SysRoleMenu, BigInteger>, SysRoleMenuExtRepository {
    List<SysRoleMenu> findByRoleId(BigInteger roleId);
}
