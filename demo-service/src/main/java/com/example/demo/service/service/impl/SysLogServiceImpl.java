package com.example.demo.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.service.mapper.SysLogMapper;
import com.example.demo.service.model.entity.SysLog;
import com.example.demo.service.service.ISysLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统操作日志 服务实现类
 * </p>
 *
 * @author cjy
 * @since 2023-07-13
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

}
