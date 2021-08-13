package com.starter.base.controller.vo;

import com.starter.base.model.BaseModel;
import lombok.Data;

import java.util.List;

@Data
public class SysUserVo extends BaseModel {
    private String name;

    private String nickName;

    private String avatar;

    private String email;

    private String mobile;

    private Byte status;

    private Byte delFlag;

    // 非数据库字段
    private String deptName;
    private List<SysRoleVo> roles;
}
