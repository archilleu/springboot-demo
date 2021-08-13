package com.starter.base.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SysConfigDto {
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
