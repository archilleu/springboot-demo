package com.hoya.admin.server.sys.impl;

import java.util.List;

import com.hoya.admin.dao.sys.SysConfigMapper;
import com.hoya.admin.model.sys.SysConfig;
import com.hoya.admin.server.sys.SysConfigService;
import com.hoya.core.page.PageHelper;
import com.hoya.core.page.PageRequest;
import com.hoya.core.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysConfigServiceImpl implements SysConfigService {

    @Autowired
    private SysConfigMapper sysConfigMapper;

    @Override
    public int save(SysConfig record) {
        if (record.getId() == null || record.getId() == 0) {
            return sysConfigMapper.insertSelective(record);
        }
        return sysConfigMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int delete(SysConfig record) {
        return sysConfigMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<SysConfig> records) {
        for (SysConfig record : records) {
            delete(record);
        }
        return 1;
    }

    @Override
    public SysConfig findById(Long id) {
        return sysConfigMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        Object label = pageRequest.getParam("label");
        if (label != null) {
            return PageHelper.findPage(pageRequest, sysConfigMapper, "findPageByLabel", label);
        }
        return PageHelper.findPage(pageRequest, sysConfigMapper);
    }

    @Override
    public List<SysConfig> findByLable(String lable) {
        return sysConfigMapper.findByLable(lable);
    }

}
