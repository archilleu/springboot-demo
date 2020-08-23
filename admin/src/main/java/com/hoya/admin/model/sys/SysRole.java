package com.hoya.admin.model.sys;

import com.hoya.admin.model.BaseModel;
import lombok.Data;

@Data
public class SysRole extends BaseModel {

    private String name;

    private String remark;

    private Byte delFlag;

}

