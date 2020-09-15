package com.hoya.admin.server.sys;

import com.hoya.admin.model.sys.SysDept;
import com.hoya.core.service.CurdService;

import java.util.List;

public interface SysDeptService extends CurdService<SysDept> {

    /**
     * 获取整颗机构树
     *
     * @return
     */
    List<SysDept> getTree();

    /**
     * 根据parentId查询子机构
     * @param parentId
     * @return
     */
    List<SysDept> findByParentId(Long parentId);
}
