package com.example.demo.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.auth.vo.TokenUser;
import com.example.common.base.exception.ServerException;
import com.example.common.base.vo.PageRequest;
import com.example.demo.service.mapper.SysDictMapper;
import com.example.demo.service.model.entity.SysDict;
import com.example.demo.service.model.vo.input.SysDictInputVo;
import com.example.demo.service.model.vo.query.SysDictQueryVo;
import com.example.demo.service.service.ISysDictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author cjy
 * @since 2023-07-13
 */
@Slf4j
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

    @Override
    public IPage<SysDict> findPage(TokenUser tokenUser, PageRequest<SysDictQueryVo> pageRequest) {
        log.info("用户[{}]查询字典列表", tokenUser.getName());
        Page<SysDict> page = new Page<>(pageRequest.getPage(), pageRequest.getSize());
        SysDictQueryVo vo = pageRequest.getParams();

        LambdaQueryWrapper<SysDict> queryWrapper = new LambdaQueryWrapper<>();
        if (!ObjectUtils.isEmpty(vo.getLabel())) {
            queryWrapper.like(SysDict::getLabel, vo.getLabel());
        }
        if (!ObjectUtils.isEmpty(vo.getValue())) {
            queryWrapper.like(SysDict::getValue, vo.getValue());
        }
        if (!ObjectUtils.isEmpty(vo.getType())) {
            queryWrapper.like(SysDict::getType, vo.getType());
        }

        return super.page(page, queryWrapper);
    }

    @Override
    public SysDict add(TokenUser tokenUser, SysDictInputVo record) {
        log.info("用户[{}]新增字典项", tokenUser.getName());

        SysDict sysDict = new SysDict();
        BeanUtils.copyProperties(record, sysDict);
        sysDict.setCreateBy(tokenUser.getName());
        sysDict.setCreateTime(LocalDateTime.now());
        super.save(sysDict);
        return sysDict;
    }

    @Override
    public SysDict edit(TokenUser tokenUser, Long id, SysDictInputVo record) {
        log.info("用户[{}]修改字典项", tokenUser.getName());

        SysDict sysDict = super.getById(id);
        if (null == sysDict) {
            throw ServerException.NotFound;
        }
        BeanUtils.copyProperties(record, sysDict);
        sysDict.setLastUpdateBy(tokenUser.getName());
        sysDict.setLastUpdateTime(LocalDateTime.now());
        super.updateById(sysDict);
        return sysDict;
    }

    @Override
    public void delete(TokenUser tokenUser, Long id) {
        log.info("用户[{}]删除字典项", tokenUser.getName());

        super.removeById(id);
    }

    @Override
    public void delete(TokenUser tokenUser, List<Long> ids) {
        log.info("用户[{}]批量删除字典项", tokenUser.getName());

        super.removeByIds(ids);

    }
}
