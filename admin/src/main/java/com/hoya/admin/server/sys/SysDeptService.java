package com.hoya.admin.server.sys;

import com.hoya.admin.model.sys.SysDept;
import com.hoya.core.service.CurdService;

import java.util.List;

/**
 * 机构管理
 */
public interface SysDeptService extends CurdService<SysDept> {

    /**
     * 查询机构树
     *
     * @return
     */
    List<SysDept> findTree();
}
