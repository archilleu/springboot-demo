package com.starter.base.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SysDeptDto {

    @NotNull
    private String name;

    private Long parentId;

    private Integer orderNum;

    private Byte delFlag;

}
