package com.example.demo.service.model.vo.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author cjy
 */
@Data
public class UserPasswordInputVo {
    @ApiModelProperty(value = "用户旧密码", example = "cjy")
    String oldPassword;

    @NotNull
    @ApiModelProperty(value = "用户新密码", example = "cjy")
    String newPassword;
}
