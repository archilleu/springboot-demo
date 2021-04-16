package com.hoya.admin.controller.user;

import com.hoya.admin.constant.SysConstants;
import com.hoya.admin.controller.user.vo.MenuVo;
import com.hoya.admin.model.sys.SysMenu;
import com.hoya.admin.server.sys.SysMenuService;
import com.hoya.admin.util.SecurityUtils;
import com.hoya.core.exception.ServerError;
import com.hoya.core.exception.ServerException;
import com.hoya.core.exception.ServerExceptionForbidden;
import com.hoya.core.exception.ServerExceptionServerError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class MenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping(value = "/menu/findNavTree")
    public List<MenuVo> findNavTree() {
        //获取当前登陆用户
        String userName = SecurityUtils.getUsername();
        try {
            List<SysMenu> navList = null;
            if(SysConstants.ADMIN.equals(userName)) {
                navList = sysMenuService.findNavTree();
            } else {
                navList = sysMenuService.findNavTreeByUsername(userName);
            }

            return createMenuTree(navList);
        } catch (Exception e) {
            log.error(e.toString());
            throw ServerException.ServerError;
        }
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
        for(SysMenu sysMenu : menus){
            MenuVo menuVo = new MenuVo();
            BeanUtils.copyProperties(sysMenu, menuVo, "delFlag");
            children.add(menuVo);
            createMenuTree(idMenuMap, menuVo);
        }

        return;
    }
}
