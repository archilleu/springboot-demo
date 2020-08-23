package com.hoya.admin.server.sys;

import com.hoya.admin.model.sys.SysConfig;
import com.hoya.core.service.CurdService;

import java.util.List;

/**
 * 系统配置管理
 */
public interface SysConfigService extends CurdService<SysConfig> {

    /**
     * 根据名称查询
     *
     * @param lable
     * @return
     */
    List<SysConfig> findByLable(String lable);
}
