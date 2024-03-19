package com.example.demo.service.model.vo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cjy
 */
@Data
@ApiModel(description = "视图查询对象：用户信息")
public class SysUserQueryVo {

    @ApiModelProperty(value = "用户名称", example = "cjy")
    private String name;

    @ApiModelProperty(value = "用户昵称", example = "9527")
    private String nickName;

    @ApiModelProperty(value = "用户email", example = "cjy@qq.com")
    private String email;

    @ApiModelProperty(value = "用户手机号", example = "110")
    private String mobile;

}
