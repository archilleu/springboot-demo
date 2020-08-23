package com.hoya.admin.vo;

import lombok.Data;

@Data
public class LoginBean {

    private String account;
    private String password;
    private String captcha;

}
