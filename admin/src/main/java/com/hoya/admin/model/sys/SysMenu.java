package com.hoya.admin.model.sys;

import com.hoya.admin.model.BaseModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

@Data
public class SysMenu extends BaseModel {

    private Long parentId;

    @NotNull
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
    private List<SysMenu> children = new LinkedList<>();

}
