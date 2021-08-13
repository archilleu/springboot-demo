package com.starter.base.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class LoginDto {
    @NotNull
    private String account;

    @NotNull
    private String password;
}
