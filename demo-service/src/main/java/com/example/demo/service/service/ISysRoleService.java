package com.example.demo.service.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.auth.vo.TokenUser;
import com.example.common.base.vo.PageRequest;
import com.example.demo.service.model.entity.SysMenu;
import com.example.demo.service.model.entity.SysRole;
import com.example.demo.service.model.vo.input.SysRoleInputVo;
import com.example.demo.service.model.vo.query.SysRoleQueryVo;

import java.util.List;

/**
 * <p>
 * 角色管理 服务类
 * </p>
 *
 * @author cjy
 * @since 2023-07-13
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 新增角色
     *
     * @param tokenUser 用户token
     * @param record    角色项
     * @return 角色项
     */
    SysRole add(TokenUser tokenUser, SysRoleInputVo record);

    /**
     * 新增角色
     *
     * @param tokenUser 用户token
     * @param id        菜单id
     * @param record    角色项
     * @return 角色项
     */
    SysRole edit(TokenUser tokenUser, Long id, SysRoleInputVo record);

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
     * 分页获取数据
     *
     * @param tokenUser   用户token
     * @param pageRequest 请求参数
     * @return 分页数据
     */
    IPage<SysRole> findPage(TokenUser tokenUser, PageRequest<SysRoleQueryVo> pageRequest);

    /**
     * 根据角色获取角色菜单
     *
     * @param tokenUser 角色token
     * @param roleId    角色id
     * @return
     */
    List<SysMenu> findRoleMenus(TokenUser tokenUser, Long roleId);

    /**
     * 保存角色菜单
     *
     * @param tokenUser 角色token
     * @param roleId    角色id
     * @param menuIds   菜单id列表
     * @return 个数
     */
    int saveRoleMenus(TokenUser tokenUser, Long roleId, List<Long> menuIds);
}
