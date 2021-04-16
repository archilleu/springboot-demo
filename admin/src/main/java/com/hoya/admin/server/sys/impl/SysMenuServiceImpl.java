package com.hoya.admin.server.sys.impl;

import java.time.LocalDateTime;
import java.util.*;

import com.hoya.admin.dao.sys.SysMenuMapper;
import com.hoya.admin.model.sys.SysMenu;
import com.hoya.admin.server.sys.SysMenuService;
import com.hoya.admin.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public int save(SysMenu record) {
        if (record.getId() == null || record.getId() == 0) {
            record.setCreateBy(SecurityUtils.getUsername());
            record.setCreateTime(LocalDateTime.now());
            return sysMenuMapper.insertSelective(record);
        }
        if (record.getParentId() == null) {
            record.setParentId(0L);
        }

        return sysMenuMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int delete(SysMenu record) {
        return sysMenuMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<SysMenu> records) {
        int count = 0;
        for (SysMenu record : records) {
            count += delete(record);
        }
        return count;
    }

    @Override
    public List<SysMenu> getMenuTree() {
        return sysMenuMapper.findAll();
    }

    @Override
    public List<SysMenu> findNavTree() {
        return sysMenuMapper.findNavAll();
    }

    @Override
    public List<SysMenu> findNavTreeByUsername(String userName) {
        return sysMenuMapper.findNavByUserName(userName);
    }

    public List<SysMenu> findAll() {
        return sysMenuMapper.findAll();
    }

    public List<SysMenu> findByUsername(String userName) {
        return sysMenuMapper.findByUserName(userName);
    }
}
