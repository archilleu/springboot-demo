package com.starter.base.service.sys.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starter.base.controller.vo.SysDeptVo;
import com.starter.base.mapper.sys.SysDeptMapper;
import com.starter.base.model.sys.SysDept;
import com.starter.base.service.sys.SysDeptService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Override
    public List<SysDeptVo> getTree() {
        List<SysDeptVo> treeDept = new ArrayList<>();
        List<SysDept> depts = sysDeptMapper.selectList(null);
        for (SysDept dept : depts) {
            if (dept.getParentId() == null || dept.getParentId() == 0) {
                SysDeptVo sysDeptVo = new SysDeptVo();
                BeanUtils.copyProperties(dept, sysDeptVo);
                treeDept.add(sysDeptVo);
            }
        }
        findChildren(treeDept, depts);
        return treeDept;
    }

    // FIXME:粗暴递归，参考menu的算法高效点
    private void findChildren(List<SysDeptVo> treeDept, List<SysDept> depts) {
        for (SysDeptVo sysDept : treeDept) {
            List<SysDeptVo> children = new ArrayList<>();
            for (SysDept dept : depts) {
                if (sysDept.getId() != null && sysDept.getId().equals(dept.getParentId())) {
                    SysDeptVo sysDeptVo = new SysDeptVo();
                    BeanUtils.copyProperties(dept, sysDeptVo);
                    children.add(sysDeptVo);
                }
            }
            sysDept.setChildren(children);
            findChildren(children, depts);
        }
    }

}
