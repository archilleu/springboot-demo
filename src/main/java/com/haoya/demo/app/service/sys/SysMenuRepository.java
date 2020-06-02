package com.haoya.demo.app.service.sys;

import com.haoya.demo.app.model.sys.SysMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;

public interface SysMenuRepository extends JpaRepository<SysMenu, BigInteger>, SysMenuExtRepository {
    List<SysMenu> findByNameLike(String name);
}
