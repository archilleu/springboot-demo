package com.hoya.admin.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginBean {

    @NotNull
    private String account;

    @NotNull
    private String password;

    private String captcha;

}
