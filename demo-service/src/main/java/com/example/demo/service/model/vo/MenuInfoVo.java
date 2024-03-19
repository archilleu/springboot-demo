package com.example.demo.service.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * @author cjy
 */
@Data
@ApiModel(description = "视图对象：菜单项")
public class MenuInfoVo {

    @ApiModelProperty(value = "菜单id", example = "1")
    private Long id;

    @ApiModelProperty(value = "父菜单id", example = "0")
    private Long parentId;

    @ApiModelProperty(value = "菜单名称", example = "用户管理")
    private String name;

    @ApiModelProperty(value = "菜单url", example = "用户管理")
    private String url;

    @ApiModelProperty(value = "菜单权限", example = "用户管理")
    private String perms;

    @ApiModelProperty(value = "菜单类型", example = "用户管理")
    private Integer type;

    @ApiModelProperty(value = "菜单图标", example = "用户管理")
    private String icon;

    @ApiModelProperty(value = "菜单排序", example = "0")
    private Integer orderNum;

    @ApiModelProperty(value = "子菜单")
    private List<MenuInfoVo> children = new LinkedList<>();
}
