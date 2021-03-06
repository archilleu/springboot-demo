package com.hoya.admin.server.sys.impl;

import java.time.LocalDateTime;
import java.util.List;

import com.hoya.admin.constant.SysConstants;
import com.hoya.admin.dao.sys.SysMenuMapper;
import com.hoya.admin.dao.sys.SysRoleMapper;
import com.hoya.admin.dao.sys.SysRoleMenuMapper;
import com.hoya.admin.model.sys.SysMenu;
import com.hoya.admin.model.sys.SysRole;
import com.hoya.admin.model.sys.SysRoleMenu;
import com.hoya.admin.server.sys.SysRoleService;
import com.hoya.admin.util.SecurityUtils;
import com.hoya.core.exception.ServerExceptionForbidden;
import com.hoya.core.exception.ServerExceptionFound;
import com.hoya.core.exception.ServerExceptionNotFound;
import com.hoya.core.page.PageHelper;
import com.hoya.core.page.PageRequest;
import com.hoya.core.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public int save(SysRole record) {
        try {
            if (record.getId() == null || record.getId() == 0) {
                record.setCreateBy(SecurityUtils.getUsername());
                record.setCreateTime(LocalDateTime.now());
                return sysRoleMapper.insertSelective(record);
            }

            record.setLastUpdateBy(SecurityUtils.getUsername());
            record.setLastUpdateTime(LocalDateTime.now());
            return sysRoleMapper.updateByPrimaryKeySelective(record);
        } catch (DuplicateKeyException e) {
            throw new ServerExceptionFound("角色已经存在");
        }
    }

    @Override
    public int delete(SysRole record) {
        //TODO: 删除用户对应的，菜单对应的
        return sysRoleMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<SysRole> records) {
        int count = 0;
        for (SysRole record : records) {
            count += delete(record);
        }

        return count;
    }

    @Override
    public SysRole findById(Long id) {
        return sysRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        PageResult pageResult = PageHelper.findPage(pageRequest, sysRoleMapper, pageRequest.getParams());
        return pageResult;
    }

    @Override
    public List<SysRole> findAll() {
        return sysRoleMapper.findAll();
    }

    @Override
    public List<SysMenu> findRoleMenus(Long roleId) {
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(roleId);
        if (null == sysRole) {
            throw new ServerExceptionNotFound("角色不存在");
        }

        if (SysConstants.ADMIN_ID.equals(sysRole.getId())) {
            // 如果是超级管理员，返回全部
            return sysMenuMapper.findAll();
        }
        return sysMenuMapper.findRoleMenus(roleId);
    }

    @Transactional
    @Override
    public int saveRoleMenus(List<SysRoleMenu> records) {
        if (records.isEmpty()) {
            return 0;
        }

        for (SysRoleMenu record : records) {
            if(SysConstants.ADMIN_ID.equals(record.getId())) {
                throw new ServerExceptionForbidden("超级管理员拥有所有菜单权限，不允许修改！");
            }

            SysRole sysRole = sysRoleMapper.selectByPrimaryKey(record.getRoleId());
            if(null == sysRole) {
                throw new ServerExceptionNotFound("角色不存在");
            }
        }

        //删除旧的菜单
        Long roleId = records.get(0).getRoleId();
        sysRoleMenuMapper.deleteByRoleId(roleId);
        //插入新菜单
        for (SysRoleMenu record : records) {
            sysRoleMenuMapper.insertSelective(record);
        }

        return records.size();
    }

}
