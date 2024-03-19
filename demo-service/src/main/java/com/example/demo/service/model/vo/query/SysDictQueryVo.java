package com.example.demo.service.model.vo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cjy
 */
@Data
@ApiModel(description = "查询参数：字典信息")
public class SysDictQueryVo {

    @ApiModelProperty(value = "字典值", example = "unknown")
    private String value;

    @ApiModelProperty(value = "字典标签", example = "未知")
    private String label;

    @ApiModelProperty(value = "字典类型", example = "sex")
    private String type;
}
