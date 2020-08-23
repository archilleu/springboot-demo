package com.hoya.admin.model.sys;

import com.hoya.admin.model.BaseModel;
import lombok.Data;

import java.util.List;

@Data
public class SysMenu extends BaseModel {

    private Long parentId;

    private String name;

    private String url;

    private String perms;

    private Integer type;

    private String icon;

    private Integer orderNum;

    private Byte delFlag;

    // 非数据库字段
    private String parentName;
    // 非数据库字段
    private Integer level;
    // 非数据库字段
    private List<SysMenu> children;

}
