package com.example.demo.service.model.vo.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author cjy
 */
@Data
@ApiModel(description = "视图输入对象：角色信息")
public class SysRoleInputVo {

    @NotNull
    @ApiModelProperty(value = "角色名称", example = "prod")
    private String name;

    @NotNull
    @ApiModelProperty(value = "角色备注", example = "生产人员")
    private String remark;
}
