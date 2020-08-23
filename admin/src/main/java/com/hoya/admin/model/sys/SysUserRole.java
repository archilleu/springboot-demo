package com.hoya.admin.model.sys;

import com.hoya.admin.model.BaseModel;
import lombok.Data;

@Data
public class SysUserRole extends BaseModel {

    private Long userId;

    private Long roleId;

}

