package com.example.demo.service.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.auth.vo.TokenUser;
import com.example.common.base.vo.PageRequest;
import com.example.demo.service.model.entity.SysDict;
import com.example.demo.service.model.vo.input.SysDictInputVo;
import com.example.demo.service.model.vo.query.SysDictQueryVo;

import java.util.List;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author cjy
 * @since 2023-07-13
 */
public interface ISysDictService extends IService<SysDict> {

    /**
     * 分页获取数据
     *
     * @param tokenUser   用户token
     * @param pageRequest 请求参数
     * @return 分页数据
     */
    IPage<SysDict> findPage(TokenUser tokenUser, PageRequest<SysDictQueryVo> pageRequest);

    /**
     * 增加配置
     *
     * @param tokenUser 用户token
     * @param record    配置信息
     * @return 字典项
     */
    SysDict add(TokenUser tokenUser, SysDictInputVo record);

    /**
     * 修改配置
     *
     * @param tokenUser 用户token
     * @param id        配置项id
     * @param record    配置信息
     * @return 字典项
     */
    SysDict edit(TokenUser tokenUser, Long id, SysDictInputVo record);

    /**
     * 删除配置
     *
     * @param tokenUser 用户token
     * @param id        配置项ID
     */
    void delete(TokenUser tokenUser, Long id);

    /**
     * 批量删除配置
     *
     * @param tokenUser 用户token
     * @param ids       配置项IDs
     */
    void delete(TokenUser tokenUser, List<Long> ids);
}
