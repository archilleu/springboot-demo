package com.example.demo.service.model.vo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cjy
 */
@Data
@ApiModel(description = "查询参数：系统配置信息")
public class SysConfigQueryVo {
    @ApiModelProperty(value = "标签名", example = "theme")
    private String label;

    @ApiModelProperty(value = "类型", example = "color")
    private String type;

    @ApiModelProperty(value = "描述", example = "主题色")
    private String description;
}
