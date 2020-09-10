package com.hoya.admin.model.sys;

import com.hoya.admin.model.BaseModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class SysDept extends BaseModel {

    @NotNull
    private String name;
    
    private Long parentId;

    private Integer orderNum;

    private Byte delFlag;

    // 非数据库字段
    private List<SysDept> children;

    // 非数据库字段
    private Boolean hasChildren = true;

    // 非数据库字段
    private String parentName;

}