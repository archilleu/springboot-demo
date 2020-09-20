package com.hoya.admin.dao.sys;

import com.hoya.admin.model.sys.SysConfig;

import java.util.LinkedHashMap;
import java.util.List;

public interface SysConfigMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysConfig record);

    int insertSelective(SysConfig record);

    SysConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysConfig record);

    int updateByPrimaryKey(SysConfig record);

    /**
     * 列表查询
     * 查询条件：label，value
     * @param params
     * @return
     */
    List<SysConfig> findPage(LinkedHashMap<String, Object> params);
}