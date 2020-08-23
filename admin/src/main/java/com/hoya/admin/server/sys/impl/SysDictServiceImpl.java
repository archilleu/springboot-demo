package com.hoya.admin.server.sys.impl;

import com.hoya.admin.dao.sys.SysDictMapper;
import com.hoya.admin.model.sys.SysDict;
import com.hoya.admin.server.sys.SysDictService;
import com.hoya.core.page.PageHelper;
import com.hoya.core.page.PageRequest;
import com.hoya.core.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysDictServiceImpl implements SysDictService {

    @Autowired
    private SysDictMapper sysDictMapper;

    @Override
    public int save(SysDict record) {
        if (record.getId() == null || record.getId() == 0) {
            return sysDictMapper.insertSelective(record);
        }
        return sysDictMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int delete(SysDict record) {
        return sysDictMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<SysDict> records) {
        int count = 0;
        for (SysDict record : records) {
            count += delete(record);
        }
        return count;
    }

    @Override
    public SysDict findById(Long id) {
        return sysDictMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        Object label = pageRequest.getParam("label");
        if (label != null) {
            return PageHelper.findPage(pageRequest, sysDictMapper, "findPageByLabel", label);
        }
        return PageHelper.findPage(pageRequest, sysDictMapper);
    }

}

