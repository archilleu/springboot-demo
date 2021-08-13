package com.starter.base.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starter.base.model.sys.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 获取用户所有的菜单项
     *
     * @param userName
     * @return
     */
    List<SysMenu> findByUserName(@Param(value = "userName") String userName);

    /**
     * 获取用户导航菜单（不包含按钮）
     *
     * @return
     */
    List<SysMenu> findNavAll();

    List<SysMenu> findNavByUserName(@Param(value = "userName") String userName);

    /**
     * 获取角色菜单
     *
     * @param roleId
     * @return
     */
    List<SysMenu> findRoleMenus(@Param(value = "roleId") Long roleId);
}