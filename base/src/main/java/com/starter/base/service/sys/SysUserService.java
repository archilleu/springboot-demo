package com.starter.base.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.starter.base.controller.vo.SysUserRolesVo;
import com.starter.base.model.sys.SysRole;
import com.starter.base.model.sys.SysUser;
import com.starter.common.page.PageRequest;
import com.starter.common.page.PageResult;

import java.util.List;
import java.util.Set;

public interface SysUserService extends IService<SysUser> {

    PageResult findPage(PageRequest pageRequest);

    SysUser findByUsername(String username);

    String login(String username, String password);

    Set<String> findPermissions(String userName);

    List<SysRole> findUserRoles(Long userId);

    int saveUserRoles(SysUserRolesVo sysUserRolesBean);

}
