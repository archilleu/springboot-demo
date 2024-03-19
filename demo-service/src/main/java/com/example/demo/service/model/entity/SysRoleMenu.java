package com.example.demo.service.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.service.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 角色菜单
 * </p>
 *
 * @author cjy
 * @since 2023-07-17
 */
@Data
@TableName("sys_role_menu")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysRoleMenu对象", description = "角色菜单")
public class SysRoleMenu extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("角色ID")
    private Long roleId;

    @ApiModelProperty("菜单ID")
    private Long menuId;

}
