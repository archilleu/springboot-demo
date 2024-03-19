package com.example.demo.service.model.vo.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author cjy
 */
@Data
public class SysUserInputVo {

    @NotNull
    @ApiModelProperty(value = "用户名称", example = "cjy")
    private String name;

    @NotNull
    @ApiModelProperty(value = "用户昵称", example = "9527")
    private String nickName;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "用户密码", example = "123456")
    private String password;

    @ApiModelProperty(value = "用户email", example = "cjy@qq.com")
    private String email;

    @ApiModelProperty(value = "用户手机号", example = "110")
    private String mobile;

    @ApiModelProperty(value = "用户所属部门id", example = "1")
    private Long deptId;

}
