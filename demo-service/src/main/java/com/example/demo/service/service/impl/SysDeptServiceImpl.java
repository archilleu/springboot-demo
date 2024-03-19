package com.example.demo.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.auth.vo.TokenUser;
import com.example.common.base.exception.ServerException;
import com.example.demo.service.mapper.SysDeptMapper;
import com.example.demo.service.model.entity.SysDept;
import com.example.demo.service.model.vo.SysDeptVo;
import com.example.demo.service.model.vo.input.SysDeptInputVo;
import com.example.demo.service.service.ISysDeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 机构管理 服务实现类
 * </p>
 *
 * @author cjy
 * @since 2023-07-13
 */
@Slf4j
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {

    @Override
    public SysDeptVo add(TokenUser tokenUser, SysDeptInputVo record) {
        log.info("用户[{}]新增部门", tokenUser.getName());

        SysDept sysDept = new SysDept();
        BeanUtils.copyProperties(record, sysDept);
        if (sysDept.getParentId() == null) {
            sysDept.setParentId(0L);
        }
        sysDept.setCreateBy(tokenUser.getName());
        sysDept.setCreateTime(LocalDateTime.now());
        super.save(sysDept);

        SysDeptVo sysDeptVo = new SysDeptVo();
        BeanUtils.copyProperties(sysDept, sysDeptVo);
        return sysDeptVo;
    }

    @Override
    public SysDeptVo edit(TokenUser tokenUser, Long id, SysDeptInputVo record) {
        log.info("用户[{}]修改部门", tokenUser.getName());

        SysDept sysDept = super.getById(id);
        if (null == sysDept) {
            throw ServerException.NotFound;
        }
        BeanUtils.copyProperties(record, sysDept);
        sysDept.setLastUpdateBy(tokenUser.getName());
        sysDept.setLastUpdateTime(LocalDateTime.now());

        super.updateById(sysDept);
        SysDeptVo sysDeptVo = new SysDeptVo();
        BeanUtils.copyProperties(sysDept, sysDeptVo);
        return sysDeptVo;
    }

    @Override
    public void delete(TokenUser tokenUser, Long id) {
        log.info("用户[{}]删除部门", tokenUser.getName());

        super.removeById(id);
    }

    @Override
    public void delete(TokenUser tokenUser, List<Long> ids) {
        log.info("用户[{}]批量删除部门", tokenUser.getName());

        super.removeByIds(ids);
    }

    @Override
    public List<SysDeptVo> list(TokenUser tokenUser, Long id) {
        log.info("用户[{}]查询部门列表", tokenUser.getName());

        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDept::getParentId, id);
        List<SysDept> list = super.list(queryWrapper);
        List<SysDeptVo> result = new LinkedList<>();
        list.forEach(item -> {
            SysDeptVo sysDeptVo = new SysDeptVo();
            BeanUtils.copyProperties(item, sysDeptVo);
            result.add(sysDeptVo);
        });

        return result;
    }

    @Override
    public List<SysDeptVo> tree(TokenUser tokenUser) {
        log.info("用户[{}]查询部门树形结构", tokenUser.getName());

        List<SysDeptVo> treeDept = new LinkedList<>();
        List<SysDept> list = super.list(null);

        // 顶级部门
        for (SysDept dept : list) {
            if (dept.getParentId() == null || dept.getParentId() == 0) {
                SysDeptVo sysDeptVo = new SysDeptVo();
                BeanUtils.copyProperties(dept, sysDeptVo);
                treeDept.add(sysDeptVo);
            }
        }

        // 递归子部门
        findChildren(treeDept, list);
        return treeDept;
    }

    /**
     * FIXME:粗暴递归，参考menu的算法高效点
     *
     * @param treeDept
     * @param list
     */
    private void findChildren(List<SysDeptVo> treeDept, List<SysDept> list) {
        for (SysDeptVo sysDept : treeDept) {
            List<SysDeptVo> children = new LinkedList<>();
            for (SysDept dept : list) {
                if (sysDept.getId() != null && sysDept.getId().equals(dept.getParentId())) {
                    SysDeptVo sysDeptVo = new SysDeptVo();
                    BeanUtils.copyProperties(dept, sysDeptVo);
                    children.add(sysDeptVo);
                }
            }
            sysDept.setChildren(children);
            findChildren(children, list);
        }
    }
}
