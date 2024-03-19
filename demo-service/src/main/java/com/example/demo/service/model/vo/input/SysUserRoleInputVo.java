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
@ApiModel(description = "视图输入对象：用户角色信息")
public class SysUserRoleInputVo {

    @NotNull
    @ApiModelProperty(value = "用户Id", example = "1")
    private Long userId;

    @NotNull
    @ApiModelProperty(value = "角色id列表", example = "[1767442492023648257]")
    private List<Long> roleIds;
}
