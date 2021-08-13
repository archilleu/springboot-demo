package com.starter.base.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.starter.base.controller.vo.SysDeptVo;
import com.starter.base.model.sys.SysDept;

import java.util.List;

public interface SysDeptService extends IService<SysDept> {

    /**
     * 获取整颗机构树
     *
     * @return
     */
    List<SysDeptVo> getTree();
}
