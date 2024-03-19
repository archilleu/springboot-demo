package com.example.demo.service.model.vo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author cjy
 */
@Data
@ApiModel(description = "视图查询对象：角色信息")
public class SysRoleQueryVo {

    @NotNull
    @ApiModelProperty(value = "角色名称", example = "prod")
    private String name;

}
