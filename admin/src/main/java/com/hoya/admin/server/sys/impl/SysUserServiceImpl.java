package com.hoya.admin.server.sys.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.hoya.admin.dao.sys.SysRoleMapper;
import com.hoya.admin.dao.sys.SysUserMapper;
import com.hoya.admin.dao.sys.SysUserRoleMapper;
import com.hoya.admin.model.sys.SysMenu;
import com.hoya.admin.model.sys.SysRole;
import com.hoya.admin.model.sys.SysUser;
import com.hoya.admin.model.sys.SysUserRole;
import com.hoya.admin.server.sys.SysMenuService;
import com.hoya.admin.server.sys.SysUserService;
import com.hoya.core.page.PageHelper;
import com.hoya.core.page.PageRequest;
import com.hoya.core.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Long id = null;
        if (record.getId() == null || record.getId() == 0) {
            // 新增用户
            sysUserMapper.insertSelective(record);
            id = record.getId();
        } else {
            // 更新用户信息
            sysUserMapper.updateByPrimaryKeySelective(record);
        }
        // 更新用户角色
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
        return sysUserMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<SysUser> records) {
        for (SysUser record : records) {
            delete(record);
        }
        return 1;
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
        PageResult pageResult = null;
        Object name = pageRequest.getParam("name");
        Object email = pageRequest.getParam("email");
        if (name != null) {
            if (email != null) {
                pageResult = PageHelper.findPage(pageRequest, sysUserMapper, "findPageByNameAndEmail", name, email);
            } else {
                pageResult = PageHelper.findPage(pageRequest, sysUserMapper, "findPageByName", name);
            }
        } else {
            pageResult = PageHelper.findPage(pageRequest, sysUserMapper);
        }
        // 加载用户角色信息
        findUserRoles(pageResult);
        return pageResult;
    }

    /**
     * 加载用户角色
     *
     * @param pageResult
     */
    private void findUserRoles(PageResult pageResult) {
        List<?> content = pageResult.getData();
        for (Object object : content) {
            SysUser sysUser = (SysUser) object;
            List<SysUserRole> userRoles = findUserRoles(sysUser.getId());
            sysUser.setUserRoles(userRoles);
            sysUser.setRoleNames(getRoleNames(userRoles));
        }
    }

    private String getRoleNames(List<SysUserRole> userRoles) {
        StringBuilder sb = new StringBuilder();
        for (Iterator<SysUserRole> iter = userRoles.iterator(); iter.hasNext(); ) {
            SysUserRole userRole = iter.next();
            SysRole sysRole = sysRoleMapper.selectByPrimaryKey(userRole.getRoleId());
            if (sysRole == null) {
                continue;
            }
            sb.append(sysRole.getRemark());
            if (iter.hasNext()) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    @Override
    public Set<String> findPermissions(String userName) {
        Set<String> perms = new HashSet<>();
        List<SysMenu> sysMenus = sysMenuService.findByUser(userName);
        for (SysMenu sysMenu : sysMenus) {
            if (sysMenu.getPerms() != null && !"".equals(sysMenu.getPerms())) {
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
