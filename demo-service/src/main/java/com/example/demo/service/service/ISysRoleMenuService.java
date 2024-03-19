package com.example.demo.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.service.model.entity.SysRoleMenu;

/**
 * <p>
 * 角色菜单 服务类
 * </p>
 *
 * @author cjy
 * @since 2023-07-13
 */
public interface ISysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 根据角色id删除角色菜单映射
     *
     * @param roleId 角色id
     */
    void deleteByRoleId(Long roleId);
}
