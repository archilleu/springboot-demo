package com.haoya.demo.app.sys.repository;

import com.haoya.demo.app.sys.entity.SysRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface SysRoleRepository extends JpaRepository<SysRole, BigInteger> {
    Page<SysRole> findByRoleNameLikeAndRemarkLike(String roleName, String remark, Pageable pageable);
}
