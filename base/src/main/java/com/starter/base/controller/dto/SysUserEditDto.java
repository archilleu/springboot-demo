package com.starter.base.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SysUserEditDto {

    @NotNull
    private String nickName;

    private String avatar;

    private String email;

    private String mobile;

    private String password;

    private Long deptId;
}
