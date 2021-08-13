package com.starter.base.service.sys.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starter.base.mapper.sys.SysConfigMapper;
import com.starter.base.model.sys.SysConfig;
import com.starter.base.service.sys.SysConfigService;
import org.springframework.stereotype.Service;

@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {
}
