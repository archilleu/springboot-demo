package com.hoya.admin.server.sys.impl;

import java.time.LocalDateTime;
import java.util.List;

import com.hoya.admin.dao.sys.SysLoginLogMapper;
import com.hoya.admin.model.sys.SysLoginLog;
import com.hoya.admin.server.sys.SysLoginLogService;
import com.hoya.admin.util.SecurityUtils;
import com.hoya.core.page.PageHelper;
import com.hoya.core.page.PageRequest;
import com.hoya.core.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysLoginLogServiceImpl implements SysLoginLogService {

    @Autowired
    private SysLoginLogMapper sysLoginLogMapper;

    @Override
    public int save(SysLoginLog record) {
        if (record.getId() == null || record.getId() == 0) {
            record.setCreateBy(SecurityUtils.getUsername());
            record.setCreateTime(LocalDateTime.now());
            return sysLoginLogMapper.insertSelective(record);
        }
        return sysLoginLogMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int delete(SysLoginLog record) {
        return sysLoginLogMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<SysLoginLog> records) {
        for (SysLoginLog record : records) {
            delete(record);
        }
        return 1;
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return PageHelper.findPage(pageRequest, sysLoginLogMapper, pageRequest.getParams());
    }

}
