package com.starter.base.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starter.base.model.sys.SysRole;
import com.starter.base.model.sys.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    List<SysRole> findUserRoles(@Param(value = "userId") Long userId);

    int deleteByUserId(@Param(value = "userId") Long userId);

    int insertBatch(Long userId, List<Long> roleIds);
}