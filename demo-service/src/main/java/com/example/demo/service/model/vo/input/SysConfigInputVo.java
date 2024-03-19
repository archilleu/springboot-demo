package com.example.demo.service.model.vo.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 系统配置信息添加Vo
 * </p>
 *
 * @author cjy
 * @since 2023-07-17
 */
@Data
@ApiModel(description = "视图输入对象：系统配置信息")
public class SysConfigInputVo {

    @NotNull
    @ApiModelProperty(value = "数据值", example = "60/60")
    private String value;

    @NotNull
    @ApiModelProperty(value = "标签名", example = "限流参数")
    private String label;

    @NotNull
    @ApiModelProperty(value = "类型", example = "config")
    private String type;

    @ApiModelProperty(value = "描述", example = "描述")
    private String description;

    @ApiModelProperty(value = "排序（升序）", example = "1")
    private Integer sort;

    @ApiModelProperty(value = "备注信息", example = "备注")
    private String remarks;

}
