package com.hoya.admin.dao.sys;

import com.hoya.admin.model.sys.SysDict;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

public interface SysDictMapper {
    SysDict selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int insert(SysDict record);

    int insertSelective(SysDict record);

    int updateByPrimaryKeySelective(SysDict record);

    int updateByPrimaryKey(SysDict record);

    /**
     * 列表查询
     * 查询条件：label，value
     * @param params
     * @return
     */
    List<SysDict> findPage(LinkedHashMap<String, Object> params);
}
