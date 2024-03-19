package com.example.demo.service.model.vo.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author cjy
 */
@Data
@ApiModel(description = "视图输入对象：机构信息")
public class SysDeptInputVo {

    @NotNull
    @ApiModelProperty(value = "机构名称", example = "机构")
    private String name;

    @ApiModelProperty(value = "父机构id", example = "0")
    private Long parentId;

    @ApiModelProperty(value = "排序", example = "0")
    private Integer orderNum;

    @ApiModelProperty(value = "删除标志", example = "0")
    private Byte delFlag;

}
