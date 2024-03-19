package com.example.demo.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.auth.vo.TokenUser;
import com.example.common.base.exception.ServerException;
import com.example.demo.service.mapper.SysMenuMapper;
import com.example.demo.service.model.entity.SysMenu;
import com.example.demo.service.model.vo.MenuInfoVo;
import com.example.demo.service.model.vo.input.SysMenuInputVo;
import com.example.demo.service.service.ISysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * 菜单管理 服务实现类
 * </p>
 *
 * @author cjy
 * @since 2023-07-13
 */
@Slf4j
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public SysMenu add(TokenUser tokenUser, SysMenuInputVo record) {
        log.info("用户[{}]新增菜单", tokenUser.getName());

        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(record, sysMenu);
        sysMenu.setCreateBy(tokenUser.getName());
        sysMenu.setCreateTime(LocalDateTime.now());
        super.save(sysMenu);
        return sysMenu;
    }

    @Override
    public SysMenu edit(TokenUser tokenUser, Long id, SysMenuInputVo record) {
        log.info("用户[{}]编辑菜单", tokenUser.getName());

        SysMenu sysMenu = super.getById(id);
        if (null == sysMenu) {
            throw ServerException.NotFound;
        }
        BeanUtils.copyProperties(record, sysMenu);
        sysMenu.setLastUpdateBy(tokenUser.getName());
        sysMenu.setLastUpdateTime(LocalDateTime.now());
        super.updateById(sysMenu);
        return sysMenu;
    }

    @Override
    public void delete(TokenUser tokenUser, Long id) {
        log.info("用户[{}]删除菜单", tokenUser.getName());

        super.removeById(id);
    }

    @Override
    public void delete(TokenUser tokenUser, List<Long> ids) {
        log.info("用户[{}]批量删除菜单", tokenUser.getName());

        super.removeByIds(ids);
    }

    @Override
    public List<MenuInfoVo> getMenuTree(TokenUser tokenUser) {
        log.info("用户[{}]查询菜单树", tokenUser.getName());

        return createMenuTree(sysMenuMapper.selectList(null));
    }

    @Override
    public List<MenuInfoVo> findNavTree() {
        return createMenuTree(sysMenuMapper.findNavAll());
    }

    @Override
    public List<MenuInfoVo> findNavTreeByUsername(TokenUser tokenUser) {
        log.info("用户[{}]查询菜单树", tokenUser.getName());

        return createMenuTree(sysMenuMapper.findByUserName(tokenUser.getName()));
    }

    @Override
    public List<SysMenu> findAll() {
        return sysMenuMapper.selectList(null);
    }

    @Override
    public List<SysMenu> findByUsername(String username) {
        log.info("用户[{}]查询菜单树", username);

        return sysMenuMapper.findByUserName(username);
    }

    @Override
    public List<SysMenu> findRoleMenus(Long roleId) {
        return sysMenuMapper.findRoleMenus(roleId);
    }

    private List<MenuInfoVo> createMenuTree(List<SysMenu> menus) {
        Map<Long, List<SysMenu>> idMenuMap = createIdChildrenMap(menus);

        //顶级菜单
        List<SysMenu> topMenu = idMenuMap.get(0L);
        if (null == topMenu) {
            return Collections.emptyList();
        }

        MenuInfoVo root = new MenuInfoVo();
        root.setId(0L);
        List<MenuInfoVo> children = new LinkedList<>();
        root.setChildren(children);
        createMenuTree(idMenuMap, root);
        return children;
    }

    /**
     * 生成ID，children菜单关系map
     */
    private Map<Long, List<SysMenu>> createIdChildrenMap(List<SysMenu> menus) {
        Map<Long, List<SysMenu>> idMenuMap = new HashMap<>(menus.size() * 2);
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

    /**
     * 生成菜单树
     */
    private void createMenuTree(Map<Long, List<SysMenu>> idMenuMap, MenuInfoVo root) {
        // 获取当前的菜单id当作是下一级菜单的parentId
        Long parentId = root.getId();
        // 获取当前菜单的子菜单列表
        List<SysMenu> menus = idMenuMap.get(parentId);
        if (null == menus) {
            // 没有子菜单
            return;
        }

        List<MenuInfoVo> children = root.getChildren();
        for (SysMenu sysMenu : menus) {
            MenuInfoVo menuDto = new MenuInfoVo();
            BeanUtils.copyProperties(sysMenu, menuDto, "delFlag");
            children.add(menuDto);
            createMenuTree(idMenuMap, menuDto);
        }
    }
}
