package com.hoya.admin.model.sys;

import com.hoya.admin.model.BaseModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class SysUser extends BaseModel {

    @NotNull
    private String name;

    @NotNull
    private String nickName;

    private String avatar;

    @NotNull
    private String password;

    private String salt;

    private String email;

    private String mobile;

    private Byte status;

    private Long deptId;

    private Byte delFlag;

    // 非数据库字段
    private String deptName;
    // 非数据库字段,角色名称
    private List<String> roleNames;
    // 非数据库字段,用户角色
    private List<SysUserRole> userRoles;

}
