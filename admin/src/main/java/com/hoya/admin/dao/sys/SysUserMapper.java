package com.hoya.admin.dao.sys;

import com.hoya.admin.model.sys.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

public interface SysUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser findByName(@Param(value = "name") String name);

    List<SysUser> findPage(LinkedHashMap<String, Object> params);
}