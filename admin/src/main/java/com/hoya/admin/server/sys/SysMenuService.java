package com.hoya.admin.server.sys;

import com.hoya.admin.model.sys.SysMenu;
import com.hoya.core.service.CurdService;

import java.util.List;

/**
 * 菜单管理
 */
public interface SysMenuService extends CurdService<SysMenu> {

    /**
     * 获取完整的菜单树
     *
     * @return
     */
    List<SysMenu> getMenuTree();

    /**
     * 查找用户导航树(超级管理员)
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
     * 获取全部的菜单(超级管理员）,用于获取用户权限
     *
     * @return
     */
    List<SysMenu> findAll();

    /**
     * 根据用户名获取菜单,用于获取用户权限
     *
     * @param userName
     * @return
     */
    List<SysMenu> findByUsername(String userName);
}
