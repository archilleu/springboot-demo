package com.hoya.admin.server.sys.impl;

import java.time.LocalDateTime;
import java.util.*;

import com.hoya.admin.constant.SysConstants;
import com.hoya.admin.controller.sys.vo.SysUserRolesVo;
import com.hoya.admin.dao.sys.SysRoleMapper;
import com.hoya.admin.dao.sys.SysUserMapper;
import com.hoya.admin.dao.sys.SysUserRoleMapper;
import com.hoya.admin.model.sys.SysDept;
import com.hoya.admin.model.sys.SysMenu;
import com.hoya.admin.model.sys.SysRole;
import com.hoya.admin.model.sys.SysUser;
import com.hoya.admin.server.sys.SysMenuService;
import com.hoya.admin.server.sys.SysUserService;
import com.hoya.admin.util.SecurityUtils;
import com.hoya.core.exception.ServerExceptionFound;
import com.hoya.core.page.PageHelper;
import com.hoya.core.page.PageRequest;
import com.hoya.core.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
        try {
            if (record.getId() == null || record.getId() == 0) {
                // 新增用户
                record.setCreateBy(SecurityUtils.getUsername());
                record.setCreateTime(LocalDateTime.now());
                sysUserMapper.insertSelective(record);
            } else {
                // 更新用户信息
                sysUserMapper.updateByPrimaryKeySelective(record);
            }

            return 1;
        } catch (DuplicateKeyException e) {
            throw new ServerExceptionFound("用户已经存在");
        }
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
        PageResult pageResult = PageHelper.findPage(pageRequest, sysUserMapper, "findListWithRole", pageRequest.getParams());
        return pageResult;
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
    public List<SysRole> findUserRoles(Long userId) {
        return sysUserRoleMapper.findUserRoles(userId);
    }

    @Override
    public List<SysDept> findUserDept(Long userId) {
        return sysUserMapper.findUserDept(userId);
    }

    @Override
    @Transactional
    public int saveUserRoles(SysUserRolesVo sysUserRolesBean) {

        sysUserRoleMapper.deleteByUserId(sysUserRolesBean.getUserId());

        if(sysUserRolesBean.getRoles().isEmpty()) {
            return 0;
        }

        int count = sysUserRoleMapper.insertBatch(sysUserRolesBean.getUserId(), sysUserRolesBean.getRoles());
        return count;
    }
}
