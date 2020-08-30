package com.hoya.admin.server.sys;

import com.hoya.admin.model.sys.SysMenu;
import com.hoya.admin.model.sys.SysRole;
import com.hoya.admin.model.sys.SysRoleMenu;
import com.hoya.core.service.CurdService;

import java.util.List;

public interface SysRoleService extends CurdService<SysRole> {

    List<SysRole> findAll();

    List<SysMenu> findRoleMenus(Long roleId);

    int saveRoleMenus(List<SysRoleMenu> records);

    List<SysRole> findByName(String name);

}
