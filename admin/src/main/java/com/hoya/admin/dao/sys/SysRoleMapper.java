package com.hoya.admin.dao.sys;

import com.hoya.admin.model.sys.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

public interface SysRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    List<SysRole> findPage();

    List<SysRole> findAll();

    List<SysRole> findPage(LinkedHashMap<String, Object> params);

}