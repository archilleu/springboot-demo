package com.hoya.admin.model.sys;

import com.hoya.admin.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class SysLoginLog extends BaseModel {

    private String userName;

    private String status;

    private String ip;

}

