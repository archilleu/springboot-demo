package com.starter.base.controller.vo;

import com.starter.base.model.BaseModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SysRoleVo extends BaseModel {

    @NotNull
    private String name;

    private String remark;
}
