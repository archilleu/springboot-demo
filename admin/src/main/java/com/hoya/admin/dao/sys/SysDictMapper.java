package com.hoya.admin.dao.sys;

import com.hoya.admin.model.sys.SysDict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDictMapper {

    /**
     * 根据id获取值
     *
     * @param id 主键id
     * @return SysDictController
     */
    SysDict selectByPrimaryKey(Long id);

    /**
     * 删除
     *
     * @param id 主键id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入值
     *
     * @param record 值
     * @return 影响值行数
     */
    int insert(SysDict record);

    /**
     * 插入部分值
     *
     * @param record 值
     * @return 影响行数
     */
    int insertSelective(SysDict record);


    /**
     * 更新
     *
     * @param record 更新值
     * @return 影响行数
     */
    int updateByPrimaryKeySelective(SysDict record);

    /**
     * 更新全部值
     *
     * @param record 更新值
     * @return 影响行数
     */
    int updateByPrimaryKey(SysDict record);

    /**
     * 分页查询
     *
     * @return 字典列表
     */
    List<SysDict> findPage();

    /**
     * 根据标签名称查询
     *
     * @param label 参数名
     * @return 字典列表
     */
    List<SysDict> findByLabel(@Param(value = "label") String label);

    /**
     * 根据标签名称分页查询
     *
     * @param label 参数名
     * @return 字典列表
     */
    List<SysDict> findPageByLabel(@Param(value = "label") String label);
}
