package com.hoya.admin.dao.sys;

import com.hoya.admin.model.sys.SysLoginLog;

import java.util.LinkedHashMap;
import java.util.List;

public interface SysLoginLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysLoginLog record);

    int insertSelective(SysLoginLog record);

    SysLoginLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysLoginLog record);

    int updateByPrimaryKey(SysLoginLog record);

    List<SysLoginLog> findPage(LinkedHashMap<String, Object> params);

}