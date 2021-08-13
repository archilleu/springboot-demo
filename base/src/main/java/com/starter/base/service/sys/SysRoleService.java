package com.starter.base.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.starter.base.model.sys.SysMenu;
import com.starter.base.model.sys.SysRole;
import com.starter.base.model.sys.SysRoleMenu;

import java.util.List;

public interface SysRoleService extends IService<SysRole> {

    void deleteById(Long id);

    void deleteByIds(List<Long> ids);

    /**
     * 获取全部的角色
     *
     * @return
     */
    List<SysRole> findAll();

    /**
     * 根据角色获取角色菜单
     *
     * @param roleId
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

}
