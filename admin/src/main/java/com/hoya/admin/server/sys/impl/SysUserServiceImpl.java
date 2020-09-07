package com.hoya.admin.server.sys.impl;

import java.util.*;

import com.hoya.admin.constant.SysConstants;
import com.hoya.admin.dao.sys.SysRoleMapper;
import com.hoya.admin.dao.sys.SysUserMapper;
import com.hoya.admin.dao.sys.SysUserRoleMapper;
import com.hoya.admin.model.sys.SysMenu;
import com.hoya.admin.model.sys.SysRole;
import com.hoya.admin.model.sys.SysUser;
import com.hoya.admin.model.sys.SysUserRole;
import com.hoya.admin.server.sys.SysMenuService;
import com.hoya.admin.server.sys.SysUserService;
import com.hoya.admin.util.SecurityUtils;
import com.hoya.core.page.PageHelper;
import com.hoya.core.page.PageRequest;
import com.hoya.core.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Transactional
    @Override
    public int save(SysUser record) {
        if (record.getId() == null || record.getId() == 0) {
            // 新增用户
            record.setCreateBy(SecurityUtils.getUsername());
            record.setCreateTime(new Date());
            sysUserMapper.insertSelective(record);
        } else {
            // 更新用户信息
            sysUserMapper.updateByPrimaryKeySelective(record);
        }

        //TODO:单独做个接口 更新用户角色
        Long id = record.getId();
        if (id != null && id == 0) {
            return 1;
        }
        if (id != null) {
            for (SysUserRole sysUserRole : record.getUserRoles()) {
                sysUserRole.setUserId(id);
            }
        } else {
            sysUserRoleMapper.deleteByUserId(record.getId());
        }
        for (SysUserRole sysUserRole : record.getUserRoles()) {
            sysUserRoleMapper.insertSelective(sysUserRole);
        }

        return 1;
    }

    @Override
    public int delete(SysUser record) {
        //TODO: 删除用户对应的，菜单对应的
        return sysUserMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<SysUser> records) {
        int count = 0;
        for (SysUser record : records) {
            count += delete(record);
        }

        return count;
    }

    @Override
    public SysUser findById(Long id) {
        return sysUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public SysUser findByName(String name) {
        return sysUserMapper.findByName(name);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        PageResult pageResult = PageHelper.findPage(pageRequest, sysUserMapper, pageRequest.getParams());

        // 加载用户角色信息
        findUserRoles(pageResult);
        return pageResult;
    }

    private void findUserRoles(PageResult pageResult) {
        List<?> content = pageResult.getContent();
        for (Object object : content) {
            SysUser sysUser = (SysUser) object;
            List<SysUserRole> userRoles = findUserRoles(sysUser.getId());
            sysUser.setUserRoles(userRoles);
            sysUser.setRoleNames(getRoleNames(userRoles));
        }
    }

    private List<String> getRoleNames(List<SysUserRole> userRoles) {
        List<String> roles = new ArrayList<>();
        for (SysUserRole userRole : userRoles) {
            SysRole sysRole = sysRoleMapper.selectByPrimaryKey(userRole.getRoleId());
            if (sysRole == null) {
                continue;
            }

            roles.add(sysRole.getName());
        }

        return roles;
    }

    @Override
    public Set<String> findPermissions(String userName) {
        List<SysMenu> sysMenus;
        if (SysConstants.ADMIN.equals(userName)) {
            sysMenus = sysMenuService.findAll();
        } else {
            sysMenus = sysMenuService.findByUsername(userName);
        }
        Set<String> perms = new HashSet<>();
        for (SysMenu sysMenu : sysMenus) {
            if (!StringUtils.isEmpty(sysMenu.getPerms())) {
                perms.add(sysMenu.getPerms());
            }
        }

        return perms;
    }

    @Override
    public List<SysUserRole> findUserRoles(Long userId) {
        return sysUserRoleMapper.findUserRoles(userId);
    }

}
