package com.example.demo.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.service.model.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单管理 Mapper 接口
 * </p>
 *
 * @author cjy
 * @since 2023-07-13
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 获取用户所有的菜单项
     *
     * @param username 用户登录名
     * @return 菜单列表
     */
    List<SysMenu> findByUserName(@Param(value = "username") String username);

    /**
     * 获取用户导航菜单（不包含按钮）
     *
     * @return 菜单列表
     */
    List<SysMenu> findNavAll();

    /**
     * 根据用户获取导航菜单(不包含按钮)
     *
     * @param username 登录用户名
     * @return 菜单列表
     */
    List<SysMenu> findNavByUserName(@Param(value = "username") String username);

    /**
     * 获取角色菜单
     *
     * @param roleId 角色id
     * @return 菜单列表
     */
    List<SysMenu> findRoleMenus(@Param(value = "roleId") Long roleId);
}
