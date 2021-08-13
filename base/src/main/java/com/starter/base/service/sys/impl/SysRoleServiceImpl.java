package com.starter.base.service.sys.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starter.base.constant.SysConstants;
import com.starter.base.mapper.sys.SysMenuMapper;
import com.starter.base.mapper.sys.SysRoleMapper;
import com.starter.base.mapper.sys.SysRoleMenuMapper;
import com.starter.base.model.sys.SysMenu;
import com.starter.base.model.sys.SysRole;
import com.starter.base.model.sys.SysRoleMenu;
import com.starter.base.service.sys.SysRoleService;
import com.starter.common.exception.ServerExceptionForbidden;
import com.starter.common.exception.ServerExceptionNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public void deleteById(Long id) {
        sysRoleMenuMapper.deleteByRoleId(id);
        sysRoleMapper.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteByIds(List<Long> ids) {
        for (Long id : ids) {
            sysRoleMenuMapper.deleteByRoleId(id);
        }

        sysRoleMapper.deleteBatchIds(ids);
    }

    @Override
    public List<SysRole> findAll() {
        return sysRoleMapper.selectList(null);
    }

    @Override
    public List<SysMenu> findRoleMenus(Long roleId) {
        SysRole sysRole = sysRoleMapper.selectById(roleId);
        if (null == sysRole) {
            throw new ServerExceptionNotFound("角色不存在");
        }

        if (SysConstants.ADMIN_ID.equals(sysRole.getId())) {
            // 如果是超级管理员，返回全部
            return sysMenuMapper.selectList(null);
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
            if (SysConstants.ROLE_ADMIN_ID.equals(record.getRoleId())) {
                throw new ServerExceptionForbidden("超级管理员拥有所有菜单权限，不允许修改！");
            }

            SysRole sysRole = sysRoleMapper.selectById(record.getRoleId());
            if (null == sysRole) {
                throw new ServerExceptionNotFound("角色不存在");
            }
        }

        //删除旧的菜单
        Long roleId = records.get(0).getRoleId();
        sysRoleMenuMapper.deleteByRoleId(roleId);
        //插入新菜单
        for (SysRoleMenu record : records) {
            sysRoleMenuMapper.insert(record);
        }

        return records.size();
    }

}
