package com.example.demo.service.model.vo;

import com.example.demo.service.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author cjy
 */
@Data
@ApiModel(description = "视图对象：机构项")
public class SysDeptVo extends BaseModel {

    @ApiModelProperty(value = "机构名称", example = "机构")
    private String name;

    @ApiModelProperty(value = "机构父id", example = "1")
    private Long parentId;

    @ApiModelProperty(value = "排序", example = "1")
    private Integer orderNum;

    @ApiModelProperty(value = "删除标志", example = "0")
    private Byte delFlag;

    @ApiModelProperty(value = "子机构列表")
    private List<SysDeptVo> children;
}
