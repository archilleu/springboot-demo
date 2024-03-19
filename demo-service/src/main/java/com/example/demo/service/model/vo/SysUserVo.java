package com.example.demo.service.model.vo;

import com.example.demo.service.model.BaseModel;
import com.example.demo.service.model.entity.SysRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author cjy
 */
@Data
public class SysUserVo extends BaseModel {
    @ApiModelProperty(value = "用户名称", example = "cjy")
    private String name;

    @ApiModelProperty(value = "用户昵称", example = "9527")
    private String nickName;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "用户email", example = "cjy@qq.com")
    private String email;

    @ApiModelProperty(value = "用户手机号", example = "110")
    private String mobile;

    @ApiModelProperty(value = "用户状态", example = "1")
    private Byte status;

    @ApiModelProperty(value = "用户删除标志", example = "0")
    private Byte delFlag;

    @ApiModelProperty(value = "用户所属部门id", example = "1")
    private String deptId;

    @ApiModelProperty(value = "用户所属部门名称", example = "1")
    private String deptName;

    @ApiModelProperty(value = "用户角色")
    private List<SysRole> roles;
}
