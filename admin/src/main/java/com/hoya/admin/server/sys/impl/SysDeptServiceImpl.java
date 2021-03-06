package com.hoya.admin.server.sys.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.hoya.admin.dao.sys.SysDeptMapper;
import com.hoya.admin.model.sys.SysDept;
import com.hoya.admin.server.sys.SysDeptService;
import com.hoya.admin.util.SecurityUtils;
import com.hoya.core.exception.ServerExceptionFound;
import com.hoya.core.page.PageHelper;
import com.hoya.core.page.PageRequest;
import com.hoya.core.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class SysDeptServiceImpl implements SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Override
    public int save(SysDept record) {
        try {
            if (record.getId() == null || record.getId() == 0) {
                record.setCreateBy(SecurityUtils.getUsername());
                record.setCreateTime(LocalDateTime.now());
                return sysDeptMapper.insertSelective(record);
            }
            return sysDeptMapper.updateByPrimaryKeySelective(record);
        } catch (DuplicateKeyException e) {
            throw new ServerExceptionFound("部门已经存在");
        }
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
    public List<SysDept> getTree() {
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
    public List<SysDept> findByParentId(Long parentId) {
        return sysDeptMapper.findByParentId(parentId);
    }

    // FIXME:粗暴递归，参考menu的算法高效点
    private void findChildren(List<SysDept> treeDept, List<SysDept> depts) {
        for (SysDept sysDept : treeDept) {
            List<SysDept> children = new ArrayList<>();
            for (SysDept dept : depts) {
                if (sysDept.getId() != null && sysDept.getId().equals(dept.getParentId())) {
                    children.add(dept);
                }
            }
            sysDept.setChildren(children);
            findChildren(children, depts);
        }
    }

}
