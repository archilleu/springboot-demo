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
 * 角色管理
 * </p>
 *
 * @author cjy
 * @since 2023-07-17
 */
@Data
@TableName("sys_role")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysRole对象", description = "角色管理")
public class SysRole extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("角色名称")
    private String name;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("是否删除  -1：已删除  0：正常")
    private Boolean delFlag;

}
