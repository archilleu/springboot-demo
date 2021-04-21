package com.hoya.admin.controller.sys.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 修改用户角色
 */

@Data
public class SysUserRolesVo {
    @NotNull
    private Long userId;

    @NotNull
    private List<Long> roles;
}
