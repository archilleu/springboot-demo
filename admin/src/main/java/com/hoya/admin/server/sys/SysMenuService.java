package com.hoya.admin.server.sys;

import com.hoya.admin.model.sys.SysMenu;
import com.hoya.core.service.CurdService;

import java.util.List;

/**
 * 菜单管理
 */
public interface SysMenuService extends CurdService<SysMenu> {

    // 获取菜单类型，
    enum FindType {
        ALL, // 获取所有菜单，包含按钮
        EXCLUDE_BUTTON // 获取所有菜单，不包含按钮
    }

    /**
     * 查询菜单树,用户ID和用户名为空则查询全部
     * @param userName
     * @return
     */
    List<SysMenu> findTree(String userName, FindType findType);

    /**
     * 根据用户名查找菜单列表
     *
     * @param userName
     * @return
     */
    List<SysMenu> findByUser(String userName);
}
