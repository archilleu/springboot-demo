package com.hoya.admin.server.sys;

import com.hoya.admin.model.sys.SysMenu;
import com.hoya.admin.model.sys.SysRole;
import com.hoya.admin.model.sys.SysRoleMenu;
import com.hoya.core.service.CurdService;

import java.util.List;


/**
 * 角色管理
 */
public interface SysRoleService extends CurdService<SysRole> {

    /**
     * 查询全部
     *
     * @return
     */
    List<SysRole> findAll();

    /**
     * 查询角色菜单集合
     *
     * @return
     */
    List<SysMenu> findRoleMenus(Long roleId);

    /**
     * 保存角色菜单
     *
     * @param records
     * @return
     */
    int saveRoleMenus(List<SysRoleMenu> records);

    /**
     * 根据名称查询
     *
     * @param name
     * @return
     */
    List<SysRole> findByName(String name);

}
