package com.starter.base.service.sys.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starter.base.mapper.sys.SysLogMapper;
import com.starter.base.model.sys.SysLog;
import com.starter.base.service.sys.SysLogService;
import org.springframework.stereotype.Service;

@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {
}
