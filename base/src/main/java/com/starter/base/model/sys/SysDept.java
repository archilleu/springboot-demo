package com.starter.base.model.sys;

import com.starter.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysDept extends BaseModel {

    private String name;

    private Long parentId;

    private Integer orderNum;

    private Byte delFlag;

}