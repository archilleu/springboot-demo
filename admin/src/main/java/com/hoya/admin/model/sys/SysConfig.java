package com.hoya.admin.model.sys;

import com.hoya.admin.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper=true)
public class SysConfig extends BaseModel {

    @NotNull
    private String value;

    @NotNull
    private String label;

    @NotNull
    private String type;

    @NotNull
    private String description;

    @NotNull
    private Long sort;

    @NotNull
    private String remarks;

    private Byte delFlag;

}

