package com.starter.base.model.sys;

import com.starter.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
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

}
