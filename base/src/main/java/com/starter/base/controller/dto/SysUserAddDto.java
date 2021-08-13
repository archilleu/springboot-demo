package com.starter.base.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SysUserAddDto {

    @NotNull
    private String name;

    @NotNull
    private String nickName;

    private String avatar;

    @NotNull
    private String password;

    private String email;

    private String mobile;

    private Long deptId;
}
