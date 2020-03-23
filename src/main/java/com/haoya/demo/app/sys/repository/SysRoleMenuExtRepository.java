package com.haoya.demo.app.sys.repository;

import com.haoya.demo.app.sys.entity.SysMenu;

import java.math.BigInteger;
import java.util.List;

public interface SysRoleMenuExtRepository {
    /**
     * 获取角色可以看到的菜单
     * @param roleIds
     * @return
     */
    List<SysMenu> getRoleMenu(List<BigInteger> roleIds);

    /**
     * 修改角色可以看到的菜单
     * @param roleId
     * @param menus
     */
    void modifyRoleMenu(BigInteger roleId, List<BigInteger> menus);
}
