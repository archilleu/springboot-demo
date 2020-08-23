package com.hoya.admin.server.sys;

import com.hoya.admin.model.sys.SysDict;
import com.hoya.core.page.PageRequest;
import com.hoya.core.page.PageResult;
import com.hoya.core.service.CurdService;

import java.util.List;

public interface SysDictService extends CurdService<SysDict> {

    int save(SysDict record);

    int delete(SysDict record);

    int delete(List<SysDict> records);

    SysDict findById(Long id);

    PageResult findPage(PageRequest pageRequest);

}