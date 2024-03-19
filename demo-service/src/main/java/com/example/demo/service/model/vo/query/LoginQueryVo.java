package com.example.demo.service.model.vo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author cjy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "查询参数：登录信息")
public class LoginQueryVo {
    @NotNull
    @ApiModelProperty(value = "用户账户", example = "admin")
    private String account;

    @NotNull
    @ApiModelProperty(value = "用户密码", example = "admin")
    private String password;
}
