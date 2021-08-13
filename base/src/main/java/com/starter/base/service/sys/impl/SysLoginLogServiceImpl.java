package com.starter.base.service.sys.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starter.base.mapper.sys.SysLoginLogMapper;
import com.starter.base.model.sys.SysLoginLog;
import com.starter.base.service.sys.SysLoginLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements SysLoginLogService {

    @Resource
    private SysLoginLogMapper sysLoginLogMapper;

}
