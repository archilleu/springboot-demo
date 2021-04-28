package com.hoya.admin.server.sys.impl;

import com.hoya.admin.dao.sys.SysDictMapper;
import com.hoya.admin.model.sys.SysDict;
import com.hoya.admin.server.sys.SysDictService;
import com.hoya.admin.util.SecurityUtils;
import com.hoya.core.exception.ServerExceptionFound;
import com.hoya.core.page.PageHelper;
import com.hoya.core.page.PageRequest;
import com.hoya.core.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SysDictServiceImpl implements SysDictService {

    @Autowired
    private SysDictMapper sysDictMapper;

    @Override
    public int save(SysDict record) {
        try {
            if (record.getId() == null || record.getId() == 0) {
                record.setCreateBy(SecurityUtils.getUsername());
                record.setCreateTime(LocalDateTime.now());
                return sysDictMapper.insertSelective(record);
            }
            return sysDictMapper.updateByPrimaryKeySelective(record);
        } catch (DuplicateKeyException e) {
            throw new ServerExceptionFound("字典值已经存在");
        }
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
        return PageHelper.findPage(pageRequest, sysDictMapper, pageRequest.getParams());
    }

}

