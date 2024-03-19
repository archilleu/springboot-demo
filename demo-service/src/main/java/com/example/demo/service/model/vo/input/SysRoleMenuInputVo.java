package com.example.demo.service.model.vo.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author cjy
 */
@Data
@ApiModel(description = "视图输入对象：角色菜单信息")
public class SysRoleMenuInputVo {

    @NotNull
    @ApiModelProperty(value = "角色Id", example = "1")
    private Long roleId;

    @NotNull
    @ApiModelProperty(value = "菜单id列表", example = "[1767442492023648257]")
    private List<Long> menuIds;
}
