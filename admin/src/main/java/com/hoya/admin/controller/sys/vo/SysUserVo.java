package com.hoya.admin.controller.sys.vo;

import com.hoya.admin.model.sys.SysUserRole;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SysUserVo {

    private Long id;

    private String name;

    private String nickName;

    private String password;

    private String avatar;

    private String email;

    private String mobile;

    private Byte status;

    private Byte delFlag;

    private LocalDateTime createTime;

    // 非数据库字段
    private String deptName;
    private List<SysRoleVo> roles;
}
