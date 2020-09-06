package com.hoya.admin.server.sys.impl;

import java.util.*;

import com.hoya.admin.constant.SysConstants;
import com.hoya.admin.dao.sys.SysMenuMapper;
import com.hoya.admin.model.sys.SysMenu;
import com.hoya.admin.server.sys.SysMenuService;
import com.hoya.core.page.PageHelper;
import com.hoya.core.page.PageRequest;
import com.hoya.core.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public int save(SysMenu record) {
        if (record.getId() == null || record.getId() == 0) {
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
    public SysMenu findById(Long id) {
        return sysMenuMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return PageHelper.findPage(pageRequest, sysMenuMapper);
    }

    @Override
    public List<SysMenu> findTree(String userName, FindType findType) {
        List<SysMenu> menus = findByUser(userName);
        Map<Long, List<SysMenu>> idMenuMap = createIdChildrenMap(menus);

        //顶级菜单
        List<SysMenu> topMenu = idMenuMap.get(0L);
        if (null == topMenu) {
            return new LinkedList<>();
        }

        SysMenu root = new SysMenu();
        root.setId(0L);
        List<SysMenu> children = new LinkedList<>();
        root.setChildren(children);
        createMenuTree(idMenuMap, root);
        return children;
    }

    @Override
    public List<SysMenu> findByUser(String userName) {
        if (userName == null || "".equals(userName) || SysConstants.ADMIN.equalsIgnoreCase(userName)) {
            return sysMenuMapper.findAll();
        }
        return sysMenuMapper.findByUserName(userName);
    }

    // 生成ID，children菜单关系map
    private Map<Long, List<SysMenu>> createIdChildrenMap(List<SysMenu> menus) {
        Map<Long, List<SysMenu>> idMenuMap = new HashMap<>();
        for (SysMenu menu : menus) {
            List<SysMenu> list = idMenuMap.get(menu.getParentId());
            if (null == list) {
                list = new LinkedList<>();
                idMenuMap.put(menu.getParentId(), list);
            }
            list.add(menu);
        }

        return idMenuMap;
    }

    // 生成菜单树
    private void createMenuTree(Map<Long, List<SysMenu>> idMenuMap, SysMenu root) {
        // 获取当前的菜单id当作是下一级菜单的parentId
        Long parentId = root.getId();
        // 获取当前菜单的子菜单列表
        List<SysMenu> menus = idMenuMap.get(parentId);
        if (null == menus) {
            // 没有子菜单
            return;
        }

        List<SysMenu> children = root.getChildren();
        for(SysMenu sysMenu : menus){
            children.add(sysMenu);
            createMenuTree(idMenuMap, sysMenu);
        }

        return;
    }

}
