package com.hoya.admin.model.sys;

import com.hoya.admin.model.BaseModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SysRole extends BaseModel {

    @NotNull
    private String name;

    private String remark;

    private Byte delFlag;

}

