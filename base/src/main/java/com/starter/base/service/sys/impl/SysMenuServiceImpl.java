package com.starter.base.service.sys.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starter.base.controller.vo.MenuVo;
import com.starter.base.mapper.sys.SysMenuMapper;
import com.starter.base.model.sys.SysMenu;
import com.starter.base.service.sys.SysMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<MenuVo> getMenuTree() {
        return createMenuTree(sysMenuMapper.selectList(null));
    }

    @Override
    public List<MenuVo> findNavTree() {
        return createMenuTree(sysMenuMapper.findNavAll());
    }

    @Override
    public List<MenuVo> findNavTreeByUsername(String userName) {
        return createMenuTree(sysMenuMapper.findNavByUserName(userName));
    }

    public List<SysMenu> findAll() {
        return sysMenuMapper.selectList(null);
    }

    public List<SysMenu> findByUsername(String userName) {
        return sysMenuMapper.findByUserName(userName);
    }

    private List<MenuVo> createMenuTree(List<SysMenu> menus) {
        Map<Long, List<SysMenu>> idMenuMap = createIdChildrenMap(menus);

        //顶级菜单
        List<SysMenu> topMenu = idMenuMap.get(0L);
        if (null == topMenu) {
            return new LinkedList<>();
        }

        MenuVo root = new MenuVo();
        root.setId(0L);
        List<MenuVo> children = new LinkedList<>();
        root.setChildren(children);
        createMenuTree(idMenuMap, root);
        return children;
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
    private void createMenuTree(Map<Long, List<SysMenu>> idMenuMap, MenuVo root) {
        // 获取当前的菜单id当作是下一级菜单的parentId
        Long parentId = root.getId();
        // 获取当前菜单的子菜单列表
        List<SysMenu> menus = idMenuMap.get(parentId);
        if (null == menus) {
            // 没有子菜单
            return;
        }

        List<MenuVo> children = root.getChildren();
        for (SysMenu sysMenu : menus) {
            MenuVo menuVo = new MenuVo();
            BeanUtils.copyProperties(sysMenu, menuVo, "delFlag");
            children.add(menuVo);
            createMenuTree(idMenuMap, menuVo);
        }

        return;
    }
}
