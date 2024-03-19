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
 * 用户管理
 * </p>
 *
 * @author cjy
 * @since 2023-07-17
 */
@Data
@TableName("sys_user")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysUser对象", description = "用户管理")
public class SysUser extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户名")
    private String name;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("加密盐")
    private String salt;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("状态  0：禁用   1：正常")
    private Boolean status;

    @ApiModelProperty("机构ID")
    private Long deptId;

    @ApiModelProperty("是否删除  -1：已删除  0：正常")
    private Boolean delFlag;


}
