package com.starter.base.model.sys;

import com.starter.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysLog extends BaseModel {

    private String userName;

    private String operation;

    private String method;

    private String params;

    private Long time;

    private String ip;

}

