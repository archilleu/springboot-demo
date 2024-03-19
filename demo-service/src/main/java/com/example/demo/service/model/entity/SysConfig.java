package com.example.demo.service.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.service.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 系统配置表
 * </p>
 *
 * @author cjy
 * @since 2023-07-17
 */
@Data
@TableName("sys_config")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysConfig对象", description = "系统配置表")
public class SysConfig extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

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
