package com.hoya.admin.controller.user.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginRequestVo {

    @NotNull
    private String account;

    @NotNull
    private String password;

}
