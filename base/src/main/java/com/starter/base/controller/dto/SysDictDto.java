package com.starter.base.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SysDictDto {

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
