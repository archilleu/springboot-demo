package com.hoya.admin.controller.sys.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysRoleVo implements Serializable {
    private Long id;

    private String name;

    private String remark;
}
