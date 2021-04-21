package com.hoya.admin.server.sys.impl;

import java.time.LocalDateTime;
import java.util.List;

import com.hoya.admin.dao.sys.SysConfigMapper;
import com.hoya.admin.model.sys.SysConfig;
import com.hoya.admin.server.sys.SysConfigService;
import com.hoya.admin.util.SecurityUtils;
import com.hoya.core.exception.ServerExceptionFound;
import com.hoya.core.page.PageHelper;
import com.hoya.core.page.PageRequest;
import com.hoya.core.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class SysConfigServiceImpl implements SysConfigService {

    @Autowired
    private SysConfigMapper sysConfigMapper;

    @Override
    public int save(SysConfig record) {
        try {
            if (record.getId() == null || record.getId() == 0) {
                record.setCreateBy(SecurityUtils.getUsername());
                record.setCreateTime(LocalDateTime.now());
                return sysConfigMapper.insertSelective(record);
            }
            return sysConfigMapper.updateByPrimaryKeySelective(record);
        } catch (DuplicateKeyException e) {
            throw new ServerExceptionFound("配置项已经存在");
        }
    }

    @Override
    public int delete(SysConfig record) {
        return sysConfigMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<SysConfig> records) {
        int count = 0;
        for (SysConfig record : records) {
            count += delete(record);
        }
        return count;
    }

    @Override
    public SysConfig findById(Long id) {
        return sysConfigMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return PageHelper.findPage(pageRequest, sysConfigMapper, pageRequest.getParams());
    }

}
