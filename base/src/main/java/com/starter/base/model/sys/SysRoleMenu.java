package com.starter.base.model.sys;

import com.starter.base.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SysRoleMenu extends BaseModel {

    private Long roleId;

    private Long menuId;
}

