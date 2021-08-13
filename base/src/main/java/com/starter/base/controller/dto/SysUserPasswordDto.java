package com.starter.base.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SysUserPasswordDto {
    @NotNull
    String password;
}
