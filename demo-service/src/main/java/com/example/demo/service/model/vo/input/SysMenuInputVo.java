package com.example.demo.service.model.vo.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author cjy
 */
@Data
@ApiModel(description = "视图输入对象：菜单信息")
public class SysMenuInputVo {

    @ApiModelProperty(value = "父菜单id", example = "0")
    private Long parentId;

    @NotNull
    @ApiModelProperty(value = "菜单名", example = "测试菜单")
    private String name;

    @ApiModelProperty(value = "菜单url", example = "/test")
    private String url;

    @ApiModelProperty(value = "菜单权限", example = "all")
    private String perms;

    @ApiModelProperty(value = "类型   0：目录   1：菜单   2：按钮", example = "0")
    private Integer type;

    @ApiModelProperty(value = "菜单图标", example = "test")
    private String icon;

    @ApiModelProperty(value = "菜单排序", example = "0")
    private Integer orderNum;
}
