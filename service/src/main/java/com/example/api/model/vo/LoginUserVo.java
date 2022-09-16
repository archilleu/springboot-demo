package com.example.api.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author cjy
 */
@Data
@ApiModel("用户登录vo")
public class LoginUserVo {
    @NotNull
    @ApiModelProperty(value = "用户名", example = "cjy")
    private String name;

    @NotNull
    @ApiModelProperty(value = "密码", example = "cjy")
    private String password;
}
