package com.hoya.admin.model.sys;

import com.hoya.admin.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper=true)
public class SysUserRole extends BaseModel {

    @NotNull
    private Long userId;

    @NotNull
    private Long roleId;

}

