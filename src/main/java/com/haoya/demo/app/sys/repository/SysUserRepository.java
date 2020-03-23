package com.haoya.demo.app.sys.repository;

import com.haoya.demo.app.sys.entity.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface SysUserRepository extends JpaRepository<SysUser, BigInteger> {
    SysUser findByUsername(String username);

    Page<SysUser> findByUsernameLikeAndNicknameLike(String username, String nickname, Pageable pageable);
}
