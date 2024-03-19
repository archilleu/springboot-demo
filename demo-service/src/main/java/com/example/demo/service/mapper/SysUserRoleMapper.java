package com.example.demo.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.service.model.entity.SysRole;
import com.example.demo.service.model.entity.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户角色 Mapper 接口
 * </p>
 *
 * @author cjy
 * @since 2023-07-13
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 根据用户登录名查找用户角色
     *
     * @param username 用户登录名
     * @return 用户角色
     */
    List<SysRole> findUserRoles(String username);

    /**
     * 根据用户id删除用户角色
     *
     * @param userId 用户id
     */
    void deleteByUserId(Long userId);

    /**
     * 批量插入用户角色
     *
     * @param userId  用户id
     * @param roleIds 用户角色id
     * @return 更新条数
     */
    int insertBatch(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);
}
