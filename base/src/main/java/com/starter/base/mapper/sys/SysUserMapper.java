package com.starter.base.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starter.base.controller.vo.SysUserVo;
import com.starter.base.model.sys.SysUser;

import java.util.List;
import java.util.Map;

public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 查早带角色列表的用户
     *
     * @param params name, email
     * @return
     */
    List<SysUserVo> findListWithRole(Map<String, Object> params);
}