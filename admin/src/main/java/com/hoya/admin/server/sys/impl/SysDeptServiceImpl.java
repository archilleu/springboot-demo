package com.hoya.admin.server.sys.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hoya.admin.dao.sys.SysDeptMapper;
import com.hoya.admin.model.sys.SysDept;
import com.hoya.admin.server.sys.SysDeptService;
import com.hoya.admin.util.SecurityUtils;
import com.hoya.core.page.PageHelper;
import com.hoya.core.page.PageRequest;
import com.hoya.core.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysDeptServiceImpl implements SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Override
    public int save(SysDept record) {
        if (record.getId() == null || record.getId() == 0) {
            record.setCreateBy(SecurityUtils.getUsername());
            record.setCreateTime(new Date());
            return sysDeptMapper.insertSelective(record);
        }
        return sysDeptMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int delete(SysDept record) {
        return sysDeptMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<SysDept> records) {
        int count = 0;
        for (SysDept record : records) {
            count += delete(record);
        }
        return count;
    }

    @Override
    public SysDept findById(Long id) {
        return sysDeptMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return PageHelper.findPage(pageRequest, sysDeptMapper);
    }

    @Override
    public List<SysDept> findTree() {
        List<SysDept> treeDept = new ArrayList<>();
        List<SysDept> depts = sysDeptMapper.findAll();
        for (SysDept dept : depts) {
            if (dept.getParentId() == null || dept.getParentId() == 0) {
                treeDept.add(dept);
            }
        }
        findChildren(treeDept, depts);
        return treeDept;
    }

    @Override
    public List<SysDept> findTree(Long parentId) {
        return sysDeptMapper.findByParentId(parentId);
    }

    // 粗暴递归，参考menu的算法高效点
    private void findChildren(List<SysDept> treeDept, List<SysDept> depts) {
        for (SysDept sysDept : treeDept) {
            List<SysDept> children = new ArrayList<>();
            for (SysDept dept : depts) {
                if (sysDept.getId() != null && sysDept.getId().equals(dept.getParentId())) {
                    dept.setParentName(sysDept.getName());
                    children.add(dept);
                }
            }
            sysDept.setChildren(children);
            findChildren(children, depts);
        }
    }

}
