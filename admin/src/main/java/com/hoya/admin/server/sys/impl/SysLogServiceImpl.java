package com.hoya.admin.server.sys.impl;

import com.hoya.admin.dao.sys.SysLogMapper;
import com.hoya.admin.model.sys.SysLog;
import com.hoya.admin.server.sys.SysLogService;
import com.hoya.core.page.PageHelper;
import com.hoya.core.page.PageRequest;
import com.hoya.core.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public int save(SysLog record) {
        if (record.getId() == null || record.getId() == 0) {
            return sysLogMapper.insertSelective(record);
        }
        return sysLogMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int delete(SysLog record) {
        return sysLogMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<SysLog> records) {
        int count = 0;
        for (SysLog record : records) {
            count += delete(record);
        }
        return count;
    }

    @Override
    public SysLog findById(Long id) {
        return sysLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        Object label = pageRequest.getParam("userName");
        if (label != null) {
            return PageHelper.findPage(pageRequest, sysLogMapper, "findPageByUserName", label);
        }
        return PageHelper.findPage(pageRequest, sysLogMapper);
    }

}
