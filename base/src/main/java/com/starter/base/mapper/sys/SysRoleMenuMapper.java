package com.starter.base.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starter.base.model.sys.SysRoleMenu;

public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
    int deleteByRoleId(Long roleId);
}