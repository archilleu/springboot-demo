package com.starter.base.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SysRoleDto {

    @NotNull
    private String name;

    private String remark;

    private Byte delFlag;
}
