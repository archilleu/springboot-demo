package com.hoya.admin.server.sys;

import com.hoya.admin.model.sys.SysUser;
import com.hoya.admin.model.sys.SysUserRole;
import com.hoya.admin.vo.SysUserRolesBean;
import com.hoya.core.service.CurdService;

import java.util.List;
import java.util.Set;

public interface SysUserService extends CurdService<SysUser> {

    SysUser findByName(String username);

    Set<String> findPermissions(String userName);

    List<SysUserRole> findUserRoles(Long userId);

    int saveUserRoles(SysUserRolesBean sysUserRolesBean);

}
