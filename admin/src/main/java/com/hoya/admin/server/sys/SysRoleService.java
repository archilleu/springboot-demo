package com.hoya.admin.server.sys;

import com.hoya.admin.model.sys.SysMenu;
import com.hoya.admin.model.sys.SysRole;
import com.hoya.admin.model.sys.SysRoleMenu;
import com.hoya.core.service.CurdService;

import java.util.List;

public interface SysRoleService extends CurdService<SysRole> {

    /**
     * 获取全部的角色
     * @return
     */
    List<SysRole> findAll();

    /**
     * 根据角色获取角色菜单
     * @param roleId
     * @return
     */
    List<SysMenu> findRoleMenus(Long roleId);

    /**
     * 保存角色菜单
     * @param records
     * @return
     */
    int saveRoleMenus(List<SysRoleMenu> records);

}
