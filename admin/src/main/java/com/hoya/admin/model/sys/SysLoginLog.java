package com.hoya.admin.model.sys;

import com.hoya.admin.model.BaseModel;
import lombok.Data;

@Data
public class SysLoginLog extends BaseModel {

    private String userName;

    private String status;

    private String ip;

}

