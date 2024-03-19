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
 * 机构管理
 * </p>
 *
 * @author cjy
 * @since 2023-07-17
 */
@Data
@TableName("sys_dept")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysDept对象", description = "机构管理")
public class SysDept extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("机构名称")
    private String name;

    @ApiModelProperty("上级机构ID，一级机构为0")
    private Long parentId;

    @ApiModelProperty("排序")
    private Integer orderNum;

    @ApiModelProperty("是否删除  -1：已删除  0：正常")
    private Boolean delFlag;

}
