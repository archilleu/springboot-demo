package com.haoya.demo.app.sys.repository;

import com.haoya.demo.app.sys.entity.SysMenu;
import com.haoya.demo.app.sys.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

public interface SysMenuRepository extends JpaRepository<SysMenu, BigInteger>, SysMenuExtRepository {
    List<SysMenu> findByNameLike(String name);
}
