package com.starter.base.controller.vo;

import com.starter.base.model.BaseModel;
import lombok.Data;

import java.util.List;

@Data
public class SysDeptVo extends BaseModel {

    private String name;

    private Long parentId;

    private Integer orderNum;

    private Byte delFlag;

    // 非数据库字段
    private List<SysDeptVo> children;

    // 非数据库字段
    private Integer hasChildren;
}
