package com.example.demo.service.model.vo.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author cjy
 */
@Data
@ApiModel(description = "视图输入对象：字典信息")
public class SysDictInputVo {

    @NotNull
    @ApiModelProperty(value = "字典值", example = "unknown")
    private String value;

    @NotNull
    @ApiModelProperty(value = "字典标签", example = "未知")
    private String label;

    @NotNull
    @ApiModelProperty(value = "字典类型", example = "sex")
    private String type;

    @ApiModelProperty(value = "字典描述", example = "性别")
    private String description;

    @ApiModelProperty(value = "排序", example = "0")
    private Integer sort;

    @ApiModelProperty(value = "备注", example = "备注")
    private String remarks;
}
