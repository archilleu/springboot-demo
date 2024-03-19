package com.example.demo.service.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.auth.vo.TokenUser;
import com.example.common.base.vo.PageRequest;
import com.example.demo.service.model.entity.SysConfig;
import com.example.demo.service.model.vo.input.SysConfigInputVo;
import com.example.demo.service.model.vo.query.SysConfigQueryVo;

import java.util.List;

/**
 * <p>
 * 系统配置表 服务类
 * </p>
 *
 * @author cjy
 * @since 2023-07-13
 */
public interface ISysConfigService extends IService<SysConfig> {

    /**
     * 分页获取数据
     *
     * @param tokenUser   用户token
     * @param pageRequest 请求参数
     * @return 分页数据
     */
    IPage<SysConfig> findPage(TokenUser tokenUser, PageRequest<SysConfigQueryVo> pageRequest);

    /**
     * 增加配置
     *
     * @param tokenUser 用户token
     * @param record    配置信息
     * @return 配置项目
     */
    SysConfig add(TokenUser tokenUser, SysConfigInputVo record);

    /**
     * 修改配置
     *
     * @param tokenUser 用户token
     * @param id        配置项id
     * @param record    配置信息
     * @return 配置项目
     */
    SysConfig edit(TokenUser tokenUser, Long id, SysConfigInputVo record);

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
