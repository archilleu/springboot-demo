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
 * 系统操作日志
 * </p>
 *
 * @author cjy
 * @since 2023-07-17
 */
@Getter
@Setter
@TableName("sys_log")
@ApiModel(value = "SysLog对象", description = "系统操作日志")
public class SysLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("用户操作")
    private String operation;

    @ApiModelProperty("请求方法")
    private String method;

    @ApiModelProperty("请求参数")
    private String params;

    @ApiModelProperty("执行时长(毫秒)")
    private Long time;

    @ApiModelProperty("IP地址")
    private String ip;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新人")
    private String lastUpdateBy;

    @ApiModelProperty("更新时间")
    private Date lastUpdateTime;


}
