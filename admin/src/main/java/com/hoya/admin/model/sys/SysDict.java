package com.hoya.admin.model.sys;

import com.hoya.admin.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper=true)
public class SysDict extends BaseModel {

    @NotNull
    private String value;

    @NotNull
    private String label;

    @NotNull
    private String type;

    private String description;

    private Long sort;

    private String remarks;

    private Byte delFlag;

}