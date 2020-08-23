package com.hoya.admin.server.sys;

import com.hoya.admin.model.sys.SysUser;
import com.hoya.admin.model.sys.SysUserRole;
import com.hoya.core.service.CurdService;

import java.util.List;
import java.util.Set;

public interface SysUserService extends CurdService<SysUser> {

    SysUser findByName(String username);

    /**
     * 查找用户的菜单权限标识集合
     *
     * @param userName
     * @return 权限集合
     */
    Set<String> findPermissions(String userName);

    /**
     * 查找用户的角色集合
     *
     * @param userId 用户id
     * @return 角色集合列表
     */
    List<SysUserRole> findUserRoles(Long userId);

}
