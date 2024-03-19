package com.example.demo.service.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.auth.vo.TokenUser;
import com.example.common.base.vo.PageRequest;
import com.example.demo.service.model.entity.SysRole;
import com.example.demo.service.model.entity.SysUser;
import com.example.demo.service.model.vo.SysUserVo;
import com.example.demo.service.model.vo.input.SysUserInputVo;
import com.example.demo.service.model.vo.input.SysUserRoleInputVo;
import com.example.demo.service.model.vo.input.UserPasswordInputVo;
import com.example.demo.service.model.vo.query.SysUserQueryVo;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户管理 服务类
 * </p>
 *
 * @author cjy
 * @since 2023-07-13
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 添加用户
     *
     * @param tokenUser 用户token
     * @param record    用户信息
     * @return 返回用户信息
     */
    SysUserVo add(TokenUser tokenUser, SysUserInputVo record);

    /**
     * 修改用户
     *
     * @param tokenUser 用户token
     * @param id        用户id
     * @param record    用户信息
     * @return 返回用户信息
     */
    SysUserVo edit(TokenUser tokenUser, Long id, SysUserInputVo record);

    /**
     * 修改用户密码
     *
     * @param tokenUser 用户token
     * @param id        用户id
     * @param record    密码信息
     * @return 返回用户信息
     */
    void modifyPassword(TokenUser tokenUser, Long id, UserPasswordInputVo record);

    /**
     * 删除用户
     *
     * @param tokenUser 用户token
     * @param id        用户id
     */
    void delete(TokenUser tokenUser, Long id);

    /**
     * 批量删除用户
     *
     * @param tokenUser 用户token
     * @param ids       用户id列表
     */
    void delete(TokenUser tokenUser, List<Long> ids);

    /**
     * 保持用户角色
     *
     * @param tokenUser 用户token
     * @param record    用户角色项
     * @return 条数
     */
    int saveUserRoles(TokenUser tokenUser, SysUserRoleInputVo record);

    /**
     * 查询用户列表
     *
     * @param tokenUser   用户token
     * @param pageRequest 查询参数
     * @return 列表
     */
    IPage<SysUserVo> findPage(TokenUser tokenUser, PageRequest<SysUserQueryVo> pageRequest);

    /**
     * 根据用户名查找用户
     *
     * @param username 用户登录名
     * @return 用户信息
     */
    SysUser findByUsername(String username);

    /**
     * 根据用户查找用户权限
     *
     * @param username 用户登录名
     * @return 权限集合
     */
    Set<String> findPermissions(String username);

    /**
     * 根据用户查找用户角色
     *
     * @param username 用户登录名
     * @return 用户角色集合
     */
    List<SysRole> findUserRoles(String username);


    /**
     * 用户登录
     *
     * @param username 账号
     * @param password 密码
     * @return token
     */
    String login(String username, String password);

}
