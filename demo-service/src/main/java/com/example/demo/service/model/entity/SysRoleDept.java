package com.example.demo.service.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 角色机构
 * </p>
 *
 * @author cjy
 * @since 2023-07-17
 */
@Getter
@Setter
@TableName("sys_role_dept")
@ApiModel(value = "SysRoleDept对象", description = "角色机构")
public class SysRoleDept implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("角色ID")
    private Long roleId;

    @ApiModelProperty("机构ID")
    private Long deptId;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新人")
    private String lastUpdateBy;

    @ApiModelProperty("更新时间")
    private Date lastUpdateTime;


}
