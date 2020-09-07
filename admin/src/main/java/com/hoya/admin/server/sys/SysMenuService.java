package com.hoya.admin.server.sys;

import com.hoya.admin.model.sys.SysMenu;
import com.hoya.core.service.CurdService;

import java.util.List;

/**
 * 菜单管理
 */
public interface SysMenuService extends CurdService<SysMenu> {

    /**
     * 查找完整的菜单树
     *
     * @return
     */
    List<SysMenu> findMenuTree();

    /**
     * 根据用户名查询菜单树
     *
     * @param userName
     * @return
     */
    List<SysMenu> findMenuTree(String userName);

    /**
     * 查找全部导航树
     *
     * @return
     */
    List<SysMenu> findNavTree();

    /**
     * 根据用户名查询导航树
     *
     * @param userName
     * @return
     */
    List<SysMenu> findNavTreeByUsername(String userName);

    /**
     * 获取全部的菜单
     *
     * @return
     */
    List<SysMenu> findAll();

    /**
     * 根据用户名获取菜单
     *
     * @param userName
     * @return
     */
    List<SysMenu> findByUsername(String userName);
}
