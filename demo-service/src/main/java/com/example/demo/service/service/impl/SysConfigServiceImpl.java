package com.example.demo.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.auth.vo.TokenUser;
import com.example.common.base.exception.ServerException;
import com.example.common.base.vo.PageRequest;
import com.example.demo.service.mapper.SysConfigMapper;
import com.example.demo.service.model.entity.SysConfig;
import com.example.demo.service.model.vo.input.SysConfigInputVo;
import com.example.demo.service.model.vo.query.SysConfigQueryVo;
import com.example.demo.service.service.ISysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 系统配置表 服务实现类
 * </p>
 *
 * @author cjy
 * @since 2023-07-13
 */
@Slf4j
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {

    @Autowired
    private SysConfigMapper sysConfigMapper;

    @Override
    public IPage<SysConfig> findPage(TokenUser tokenUser, PageRequest<SysConfigQueryVo> pageRequest) {
        log.info("用户[{}]获取配置项列表信息", tokenUser.getName());

        Page<SysConfig> page = new Page<>(pageRequest.getPage(), pageRequest.getSize());
        SysConfigQueryVo params = pageRequest.getParams();

        LambdaQueryWrapper<SysConfig> queryWrapper = new LambdaQueryWrapper<>();
        if (!ObjectUtils.isEmpty(params.getLabel())) {
            queryWrapper.like(SysConfig::getLabel, params.getLabel());
        }
        if (!ObjectUtils.isEmpty(params.getType())) {
            queryWrapper.like(SysConfig::getType, params.getType());
        }
        if (!ObjectUtils.isEmpty(params.getDescription())) {
            queryWrapper.like(SysConfig::getDescription, params.getDescription());
        }

        return sysConfigMapper.selectPage(page, queryWrapper);
    }

    @Override
    public SysConfig add(TokenUser tokenUser, SysConfigInputVo record) {
        log.info("用户[{}]新增配置项", tokenUser.getName());

        SysConfig sysConfig = new SysConfig();
        BeanUtils.copyProperties(record, sysConfig);
        sysConfig.setCreateBy(tokenUser.getName());
        sysConfig.setCreateTime(LocalDateTime.now());
        sysConfig.setLastUpdateTime(sysConfig.getCreateTime());
        try {
            super.save(sysConfig);
            return sysConfig;
        } catch (DuplicateKeyException e) {
            throw ServerException.FOUND;
        }
    }

    @Override
    public SysConfig edit(TokenUser tokenUser, Long id, SysConfigInputVo record) {
        log.info("用户[{}]修改配置项", tokenUser.getName());

        SysConfig sysConfig = super.getById(id);
        if (null == sysConfig) {
            throw ServerException.NotFound;
        }
        BeanUtils.copyProperties(record, sysConfig);
        sysConfig.setLastUpdateBy(tokenUser.getName());
        sysConfig.setLastUpdateTime(LocalDateTime.now());
        try {
            super.updateById(sysConfig);
            return sysConfig;
        } catch (DuplicateKeyException ex) {
            log.error("修改配置[{}]失败,失败原因[{}]", id, ex.getLocalizedMessage());
            throw ServerException.FOUND;
        }
    }

    @Override
    public void delete(TokenUser tokenUser, Long id) {
        log.info("用户[{}]删除配置项", tokenUser.getName());

        super.removeById(id);
    }

    @Override
    public void delete(TokenUser tokenUser, List<Long> ids) {
        log.info("用户[{}]批量删除配置项", tokenUser.getName());

        super.removeByIds(ids);
    }
}
