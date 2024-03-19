package com.example.demo.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.auth.vo.TokenUser;
import com.example.demo.service.model.entity.SysMenu;
import com.example.demo.service.model.vo.MenuInfoVo;
import com.example.demo.service.model.vo.input.SysMenuInputVo;

import java.util.List;

/**
 * <p>
 * 菜单管理 服务类
 * </p>
 *
 * @author cjy
 * @since 2023-07-13
 */
public interface ISysMenuService extends IService<SysMenu> {
    /**
     * 新增菜单
     *
     * @param tokenUser 用户token
     * @param record    菜单项
     * @return 菜单项
     */
    SysMenu add(TokenUser tokenUser, SysMenuInputVo record);

    /**
     * 修改菜单
     *
     * @param tokenUser 用户token
     * @param id        菜单id
     * @param record    菜单项
     * @return 菜单项
     */
    SysMenu edit(TokenUser tokenUser, Long id, SysMenuInputVo record);

    /**
     * 删除菜单
     *
     * @param tokenUser 用户token
     * @param id        菜单id
     */
    void delete(TokenUser tokenUser, Long id);

    /**
     * 批量删除菜单
     *
     * @param tokenUser 用户token
     * @param ids       菜单id
     */
    void delete(TokenUser tokenUser, List<Long> ids);

    /**
     * 获取完整的菜单列表
     *
     * @param tokenUser 用户token
     * @return 菜单列表
     */
    List<MenuInfoVo> getMenuTree(TokenUser tokenUser);

    /**
     * 查找用户导航树(超级管理员)
     *
     * @return 菜单列表
     */
    List<MenuInfoVo> findNavTree();

    /**
     * 根据用户名查询导航树
     *
     * @param tokenUser 用户token
     * @return 菜单列表
     */
    List<MenuInfoVo> findNavTreeByUsername(TokenUser tokenUser);

    /**
     * 获取全部的菜单(超级管理员）,用于获取用户权限
     *
     * @return 菜单列表
     */
    List<SysMenu> findAll();

    /**
     * 根据用户名获取菜单,用于获取用户权限
     *
     * @param username 用户登录名
     * @return 菜单列表
     */
    List<SysMenu> findByUsername(String username);

    /**
     * 根据角色id查询菜单
     *
     * @param roleId 角色id
     * @return 菜单列表
     */
    List<SysMenu> findRoleMenus(Long roleId);
}
