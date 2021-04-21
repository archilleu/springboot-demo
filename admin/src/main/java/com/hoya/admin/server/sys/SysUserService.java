package com.hoya.admin.server.sys;

import com.hoya.admin.controller.sys.vo.SysUserRolesVo;
import com.hoya.admin.model.sys.SysDept;
import com.hoya.admin.model.sys.SysRole;
import com.hoya.admin.model.sys.SysUser;
import com.hoya.core.service.CurdService;

import java.util.List;
import java.util.Set;

public interface SysUserService extends CurdService<SysUser> {

    SysUser findByName(String username);

    Set<String> findPermissions(String userName);

    List<SysRole> findUserRoles(Long userId);

    List<SysDept> findUserDept(Long userId);

    int saveUserRoles(SysUserRolesVo sysUserRolesBean);

}
