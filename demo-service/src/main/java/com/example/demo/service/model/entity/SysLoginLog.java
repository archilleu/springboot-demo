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
 * 系统登录日志
 * </p>
 *
 * @author cjy
 * @since 2023-07-17
 */
@Getter
@Setter
@TableName("sys_login_log")
@ApiModel(value = "SysLoginLog对象", description = "系统登录日志")
public class SysLoginLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("登录状态（online:在线，登录初始状态，方便统计在线人数；login:退出登录后将online置为login；logout:退出登录）")
    private String status;

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
