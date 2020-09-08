package com.hoya.admin.model.sys;

import com.hoya.admin.model.BaseModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SysUserRole extends BaseModel {

    @NotNull
    private Long userId;

    @NotNull
    private Long roleId;

}

