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
 * 字典表
 * </p>
 *
 * @author cjy
 * @since 2023-07-17
 */
@Data
@TableName("sys_dict")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysDict对象", description = "字典表")
public class SysDict extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("数据值")
    private String value;

    @ApiModelProperty("标签名")
    private String label;

    @ApiModelProperty("类型")
    private String type;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("排序（升序）")
    private Integer sort;

    @ApiModelProperty("备注信息")
    private String remarks;

    @ApiModelProperty("是否删除  -1：已删除  0：正常")
    private Boolean delFlag;


}
